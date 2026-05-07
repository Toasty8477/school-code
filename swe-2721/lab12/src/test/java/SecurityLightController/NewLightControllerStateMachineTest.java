package SecurityLightController;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SecurityLightController.LightControllerCommandInterface.CommandActionEnum;
import SecurityLightController.LightControllerStateMachineObserverInterface.LightState;
import SecurityLightController.LightControllerStateMachineObserverInterface.LightStateIntrusionDetectedStates;

/**
 * This class will test the operation of the light controller state machine
 * class.
 */
public class NewLightControllerStateMachineTest {
    @Mock
    private LightControllerStateMachine sm;
    @Mock
    private LightDeviceInterface lightDevice;
    @Mock
    private LightTimerInterface timer;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() throws NoSuchFieldException, IllegalAccessException {
        lightDevice = Mockito.mock(LightDeviceInterface.class);
        sm = new LightControllerStateMachine();
        timer = Mockito.mock(LightTimerInterface.class);
        sm.setLight(lightDevice);
        sm.setMotionDetectedTimer(timer);
        sm.setBlinkTimer(timer);
    }

    @Test(groups = { "all", "studentsmtests" })
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        // Start by instantiating a new instance.
        LightControllerStateMachine msm = new LightControllerStateMachine();
        Field initialStateField = LightControllerStateMachine.class.getDeclaredField("currentState");
        initialStateField.setAccessible(true);

        // Now do some asserts.
        assertNotNull(msm);

        // Check that the state is correct.
        assertEquals(initialStateField.get(sm), LightState.LAMP_OFF_DAYLIGHT);
    }

    /**
     * Provide starting, transition, and ending state
     * 
     * @return Object[][]
     */
    @DataProvider(name = "destinationStateDP")
    public Object[][] destinationStateDP() {
        return new Object[][] {
                // lamp_off_daylight -> lamp_on_full_brightness
                { LightState.LAMP_OFF_DAYLIGHT, CommandActionEnum.MANUAL_SWITCH_ON,
                        LightState.LAMP_ON_FULL_BRIGHTNESS },

                // lamp_off_daylight -> lamp_off_nightime
                { LightState.LAMP_OFF_DAYLIGHT, CommandActionEnum.LIGHT_SENSOR_DARKENED,
                        LightState.LAMP_OFF_NIGHTIME },

                // lamp_on_full_brightness -> lamp_off_daylight
                { LightState.LAMP_ON_FULL_BRIGHTNESS, CommandActionEnum.MANUAL_SWITCH_OFF,
                        LightState.LAMP_OFF_DAYLIGHT },

                // lamp_on_full_brightness -> lamp_on_nightime_brightness
                { LightState.LAMP_ON_FULL_BRIGHTNESS, CommandActionEnum.LIGHT_SENSOR_DARKENED,
                        LightState.LAMP_ON_NIGHTIME_BRIGHTNESS },

                // lamp_on_nightime_brightness -> lamp_on_full_brightness
                { LightState.LAMP_ON_NIGHTIME_BRIGHTNESS, CommandActionEnum.LIGHT_SENSOR_LIGHTENED,
                        LightState.LAMP_ON_FULL_BRIGHTNESS },

                // lamp_on_nightime_brightness -> lamp_off_nightime
                { LightState.LAMP_ON_NIGHTIME_BRIGHTNESS, CommandActionEnum.MANUAL_SWITCH_OFF,
                        LightState.LAMP_OFF_NIGHTIME },

                // lamp_off_nightime -> lamp_on_nightime_brightness
                { LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.MANUAL_SWITCH_ON,
                        LightState.LAMP_ON_NIGHTIME_BRIGHTNESS },

                // lamp_off_nightime -> motion_detected
                { LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.MOTION_DETECTED,
                        LightState.MOTION_DETECTED },

                // lamp_off_nightime -> intrusion_detected
                { LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.SECURITY_ALARM_TRIPPED,
                        LightState.INTRUSION_DETECTED },

                // motion_detected -> intrusion_detected
                { LightState.MOTION_DETECTED, CommandActionEnum.SECURITY_ALARM_TRIPPED,
                        LightState.INTRUSION_DETECTED },

                // motion_detected -> lamp_off_daylight
                { LightState.MOTION_DETECTED, CommandActionEnum.LIGHT_SENSOR_LIGHTENED,
                        LightState.LAMP_OFF_DAYLIGHT },

                // intrusion_detected -> lamp_off_nightime
                { LightState.INTRUSION_DETECTED, CommandActionEnum.ALARM_CLEARED,
                        LightState.LAMP_OFF_NIGHTIME },

                // motion detected -> lamp off nightime
                { LightState.MOTION_DETECTED, CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED,
                        LightState.LAMP_OFF_NIGHTIME },
        };
    }

    @Test(groups = { "all", "studentsmtests" }, dataProvider = "destinationStateDP")
    public void testDestinationState(LightState startingState,
            CommandActionEnum event, LightState destState)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field state = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);

        // force the starting state
        state.set(sm, startingState);

        // trigger a state change
        sm.signalAction(event);

        // check that the destination state is correct.
        assertEquals(state.get(sm), destState);
    }

    /**
     * Provide transitions which should result in turnLightOff invocation
     * 
     * @return Object[][]
     */
    @DataProvider(name = "turnLightOffDP")
    public Object[][] turnLightOffDP() {
        return new Object[][] {
                { LightState.MOTION_DETECTED, CommandActionEnum.LIGHT_SENSOR_LIGHTENED }, // -> LAMP_OFF_DAYLIGHT
                { LightState.MOTION_DETECTED, CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED }, // -> LAMP_OFF_NIGHT
                { LightState.LAMP_OFF_DAYLIGHT, CommandActionEnum.LIGHT_SENSOR_DARKENED }, // -> LAMP_OFF_NIGHTIME
                { LightState.LAMP_OFF_NIGHTIME, CommandActionEnum.LIGHT_SENSOR_LIGHTENED }, // -> LAMP_OFF_DAYLIGHT
        };
    }

    @Test(groups = { "all", "studentsmtests" }, dataProvider = "turnLightOffDP")
    public void testVerifyTurnLightOff(LightState startingState, CommandActionEnum event)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field state = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);

        // force the starting state
        state.set(sm, startingState);

        // trigger a state change
        sm.signalAction(event);

        // verify that turnLightOff is called once
        verify(lightDevice, times(1)).turnLightOff();
    }

    @Test(groups = { "all", "studentsmtests" })
    public void testVerifyTurnLightOnFullBrightness()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field state = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);

        // force the starting state
        state.set(sm, LightState.LAMP_OFF_DAYLIGHT);

        // trigger a state change
        sm.signalAction(CommandActionEnum.MANUAL_SWITCH_ON);

        // verify that turnLightOff is called once
        verify(lightDevice, times(1)).turnLightOnFullBrightness();
    }

    @Test(groups = { "all", "studentsmtests" })
    public void testVerifyTurnLightOnNightimeBrightness()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field state = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);

        // force the starting state
        state.set(sm, LightState.LAMP_ON_FULL_BRIGHTNESS);

        // trigger a state change
        sm.signalAction(CommandActionEnum.LIGHT_SENSOR_DARKENED);

        // verify that turnLightOff is called once
        verify(lightDevice, times(1)).turnLightOnNightimeBrightness();
    }

    @Test(groups = { "all", "studentsmtests" })
    public void testVerifyMotionDetectedMethods()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field state = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);

        // force the starting state
        state.set(sm, LightState.LAMP_OFF_NIGHTIME);

        // trigger a state change
        sm.signalAction(CommandActionEnum.MOTION_DETECTED);

        // entry / setExpirationEvent(event:
        // CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED)
        verify(timer, times(1)).setExpirationEvent(CommandActionEnum.MOTION_DETECTION_TIMER_EXPIRED);

        // entry / startOneShotTimer(timerDuration: 30000)
        verify(timer, times(1)).startOneShotTimer(30000);

        // entry / turnLightOnFullBrightness
        verify(lightDevice, times(1)).turnLightOnFullBrightness();

        // leave the state
        sm.signalAction(CommandActionEnum.LIGHT_SENSOR_LIGHTENED);

        // exit / stopTimer
        verify(timer, times(1)).stopTimer();
    }

    @Test(groups = { "all", "studentsmtests" })
    public void testVerifyIntrusionDetectedMethods()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field state = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);

        // force the starting state
        state.set(sm, LightState.LAMP_OFF_NIGHTIME);

        // trigger a state change
        sm.signalAction(CommandActionEnum.SECURITY_ALARM_TRIPPED);

        verify(timer, times(1)).setExpirationEvent(CommandActionEnum.LAMP_TIMER_EXPIRED);
        verify(timer, times(1)).startPeriodicTimer(1000);

        // occurs twice, once on entry and once when the timer starts
        verify(lightDevice, times(2)).turnLightOnFullBrightness();

        // leave the state
        sm.signalAction(CommandActionEnum.ALARM_CLEARED);

        // exit / stopTimer
        verify(timer, times(1)).stopTimer();

        // also occurs twice
        verify(lightDevice, times(2)).turnLightOff();
    }

    @DataProvider(name = "intrusionDetectedDestinationStateDP")
    public Object[][] intrusionDetectedDestinationStateDP() {
        return new Object[][] {
                // intrusion_detected.lamp_on   -LAMP_TIMER_EXPIRED-> intrusion_detected.lamp off
                { LightStateIntrusionDetectedStates.LAMP_ON, LightStateIntrusionDetectedStates.LAMP_OFF },

                // intrusion_detected.lamp_off  -LAMP_TIMER_EXPIRED-> intrusion_detected.lamp_on
                { LightStateIntrusionDetectedStates.LAMP_OFF, LightStateIntrusionDetectedStates.LAMP_ON },
        };
    }

    @Test(groups = { "all", "studentsmtests" }, dataProvider = "intrusionDetectedDestinationStateDP")
    public void testDestinationState(LightStateIntrusionDetectedStates startingState,
            LightStateIntrusionDetectedStates destState)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field state = LightControllerStateMachine.class.getDeclaredField("intrusionDetectionSubStateVariable");
        Field currentState = LightControllerStateMachine.class.getDeclaredField("currentState");
        state.setAccessible(true);
        currentState.setAccessible(true);

        // force the starting state
        currentState.set(sm, LightState.INTRUSION_DETECTED);
        state.set(sm, startingState);

        // trigger a state change
        sm.signalAction(CommandActionEnum.LAMP_TIMER_EXPIRED);

        // check that the destination state is correct.
        assertEquals(state.get(sm), destState);
    }
}
