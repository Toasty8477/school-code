package SecurityLightController;

import java.util.ArrayList;
import java.util.List;

import SecurityLightController.LightControllerStateMachineObserverInterface.LightState;

import static SecurityLightController.LightControllerCommandInterface.CommandActionEnum.INIT;
import static SecurityLightController.LightControllerCommandInterface.CommandActionEnum.LIGHT_SENSOR_DARKENED;

/**
 * This class implements a state machine that can be used to control a security light.
 *
 * @author schilling
 */
public class NewLightControllerStateMachine implements LightControllerCommandInterface {
    /**
     * This variable holds a list of observers. Whenever a state changes, the observers are updated
     * with the state change.
     */
    private final List<LightControllerStateMachineObserverInterface> observers = new ArrayList<>();

    /**
     * This variable holds the current state for the state machine. Note the scope of the variable
     * has been set to allow easier access to this variable for testing purposes without needing to
     * use reflection.
     */
    private LightState currentState;

    /**
     * This variable holds the substate for the intrusion detected state. It will have a value of 1
     * if the lamp is on and 0 if it is off. Note that this is also available for access without the
     * usage of reflection.
     */
    private LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates intrusionDetectionSubStateVariable;

    /**
     * This variable holds a reference to the light which is to be controlled by this state machine.
     */
    private LightDeviceInterface light;

    /**
     * This variable is a reference to the timer that is to be used to control the blinking of
     * lights. This timer is periodic in nature and will continue to fire every x ms until
     * shutdown.for timed activities.
     */
    private LightTimerInterface blinkTimer;

    /**
     * This variable is a reference to the timer that is to be used for one shot activities. This
     * timer will fire once after x ms have gone by.
     */
    private LightTimerInterface motionDetectedTimer;

    /**
     * This is the default constructor, which will instantiate a new instance of this class.
     */
    public NewLightControllerStateMachine() {
        currentState = LightState.LAMP_OFF_DAYLIGHT;
        intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_OFF;
    }

    /**
     * This method will set the given light that is controlled by this state machine.
     *
     * @param light This is the instance of the light that is to be directly controlled.
     */
    public void setLight(LightDeviceInterface light) {
        this.light = light;
    }

    /**
     * This method will set an instance of the timer that is to be used with this class.
     *
     * @param blinkTimer This is the timer instance.
     */
    public void setBlinkTimer(LightTimerInterface blinkTimer) {
        this.blinkTimer = blinkTimer;
    }

    /**
     * This method will set the instance of the timer that is to be used for motion detection.
     *
     * @param motionDetectedTimer This si the timer instance.
     */
    public void setMotionDetectedTimer(LightTimerInterface motionDetectedTimer) {
        this.motionDetectedTimer = motionDetectedTimer;
    }

    /*
     * (non-Javadoc)
     * @seeSecurityLightController.LightControllerCommandInterface#subscribe(
     * SecurityLightController.LightControllerStateMachineObserverInterface)
     */
    @Override
    public void subscribe(LightControllerStateMachineObserverInterface obs) {
        observers.add(obs);
    }

    /*
     * (non-Javadoc)
     * @seeSecurityLightController.LightControllerCommandInterface#unsubscribe(
     * SecurityLightController.LightControllerStateMachineObserverInterface)
     */
    @Override
    public void unsubscribe(LightControllerStateMachineObserverInterface obs) {
        observers.remove(obs);
    }

    /*
     * (non-Javadoc)
     * @see SecurityLightController.LightControllerCommandInterface#signalAction(int)
     */
    @Override
    public void signalAction(CommandActionEnum signal) {
        LightState presentState = currentState;
        LightState resultantState = null;

        // Horrible terrible if-else tree because I can't think of a better way to do this
        if (presentState.equals(LightState.LAMP_OFF_DAYLIGHT)) {
            if (signal.equals(CommandActionEnum.MANUAL_SWITCH_ON)) {
                resultantState = LightState.LAMP_ON_FULL_BRIGHTNESS;
            } else if (signal.equals(CommandActionEnum.LIGHT_SENSOR_DARKENED)) {
                resultantState = LightState.LAMP_OFF_NIGHTIME;
            }
        } else if (presentState.equals(LightState.LAMP_OFF_NIGHTIME)) {
            if (signal.equals(CommandActionEnum.MANUAL_SWITCH_ON)) {
                resultantState = LightState.LAMP_ON_NIGHTIME_BRIGHTNESS;
            } else if (signal.equals(CommandActionEnum.LIGHT_SENSOR_LIGHTENED)) {
                resultantState = LightState.LAMP_OFF_DAYLIGHT;
            } else if (signal.equals(CommandActionEnum.MOTION_DETECTED)) {
                resultantState = LightState.MOTION_DETECTED;
            } else if (signal.equals(CommandActionEnum.SECURITY_ALARM_TRIPPED)) {
                resultantState = LightState.INTRUSION_DETECTED;
            }
        } else if (presentState.equals(LightState.LAMP_ON_FULL_BRIGHTNESS)) {
            if (signal.equals(CommandActionEnum.MANUAL_SWITCH_OFF)) {
                resultantState = LightState.LAMP_OFF_DAYLIGHT;
            } else if (signal.equals(CommandActionEnum.LIGHT_SENSOR_DARKENED)) {
                resultantState = LightState.LAMP_ON_NIGHTIME_BRIGHTNESS;
            }
        } else if (presentState.equals(LightState.LAMP_ON_NIGHTIME_BRIGHTNESS)) {
            if (signal.equals(CommandActionEnum.MANUAL_SWITCH_OFF)) {
                resultantState = LightState.LAMP_OFF_NIGHTIME;
            } else if (signal.equals(CommandActionEnum.LIGHT_SENSOR_LIGHTENED)) {
                resultantState = LightState.LAMP_ON_FULL_BRIGHTNESS;
            }
        } else if (presentState.equals(LightState.MOTION_DETECTED)) {
            if (signal.equals(CommandActionEnum.SECURITY_ALARM_TRIPPED)) {
                resultantState = LightState.INTRUSION_DETECTED;
            } else if (signal.equals(CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED)) {
                resultantState = LightState.LAMP_OFF_NIGHTIME;
            } else if (signal.equals(CommandActionEnum.LIGHT_SENSOR_LIGHTENED)) {
                resultantState = LightState.LAMP_OFF_DAYLIGHT;
            }
        } else if (presentState.equals(LightState.INTRUSION_DETECTED)) {
            if (signal.equals(CommandActionEnum.ALARM_CLEARED)) {
                resultantState = LightState.LAMP_OFF_NIGHTIME;
            }
        }

        if (resultantState != null) {
            // Run exit actions for the current state
            handleExit(presentState);
            // Run entry actions for the new state
            handleEntry(resultantState);
            // Set the state to the new state
            currentState = resultantState;
            updateObservers(resultantState);
        } else if (presentState.equals(LightState.INTRUSION_DETECTED)) {
            // No actual state change but we still have to do intrusion stuff
            manageIntrusionDetectedState(signal);
        }
    }

    private void handleExit(LightState state) {
        switch (state) {
        case MOTION_DETECTED -> {
            motionDetectedTimer.stopTimer();
        }
        case INTRUSION_DETECTED -> {
            light.turnLightOff();
            blinkTimer.stopTimer();
        }
        default -> {
            // Just do nothing becasue no exit actions to run
        }
        }
    }

    private void handleEntry(LightState state) {
        switch (state) {
        case LAMP_OFF_DAYLIGHT -> {
            light.turnLightOff();
        }
        case LAMP_ON_FULL_BRIGHTNESS -> {
            light.turnLightOnFullBrightness();
        }
        case LAMP_OFF_NIGHTIME -> {
            light.turnLightOff();
        }
        case LAMP_ON_NIGHTIME_BRIGHTNESS -> {
            light.turnLightOnNightimeBrightness();
        }
        case MOTION_DETECTED -> {
            motionDetectedTimer
                    .setExpirationEvent(CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED);
            motionDetectedTimer.startOneShotTimer(30000);
            light.turnLightOnFullBrightness();
        }
        case INTRUSION_DETECTED -> {
            // Adjust the light setting.
            light.turnLightOnFullBrightness();
            // Invoke the method which manages the substate machine.
            manageIntrusionDetectedState(CommandActionEnum.INIT);
        }
        default -> {
            // No entry conditions for other states
        }
        }
    }

    // This is almost ripped straight from the old version because it works fine
    private void manageIntrusionDetectedState(CommandActionEnum request) {
        switch (request) {
        case INIT -> {
            intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_ON;
            // Start the timer.
            blinkTimer.setExpirationEvent(CommandActionEnum.LAMP_TIMER_EXPIRED);
            blinkTimer.startPeriodicTimer(1000);
            light.turnLightOnFullBrightness();
        }
        case LAMP_TIMER_EXPIRED -> {
            if (intrusionDetectionSubStateVariable == LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_ON) {
                intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_OFF;
                light.turnLightOff();
            } else {
                intrusionDetectionSubStateVariable = LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates.LAMP_ON;
                light.turnLightOnFullBrightness();
            }
        }
        default -> {
            // No other states to handle, just gets rid of an annoying warning
        }
        }
    }

    // Update observers ripped from the old state machine
    private void updateObservers(LightState state) {
        for (LightControllerStateMachineObserverInterface obs : this.observers) {
            obs.updateLightState(state);
        }
    }
}
