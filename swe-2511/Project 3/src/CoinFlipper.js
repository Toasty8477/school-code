// Class: SWE2511 - Coin Flipper DOM
// Name: Alex Horton
// Class Section: 111


window.onload = () => {
	/* TODO - Set up events and code to process user input */
	const goButton = document.getElementById("go")
	goButton.onclick = () => {

		const getInputs = () => {
			const inputs = Array(2)
			inputs[0] = document.getElementById("coins").value
			inputs[1] = document.getElementById("flips").value
			return inputs
		}

		const error1 = (value) => {
			const error = document.getElementById("error1")
			if (value === "") {
				error.innerHTML = `Input value '${value}' is not valid. No input given.`
				error.style.visibility = "visible"
				return true
			} else if (value < 1 || value > 10 ) {
				error.innerHTML = `Input value '${value}' is not valid. Input must be >= 1 and <= 10.`
				error.style.visibility = "visible"
				return true
			} else if (isNaN(value) || !Number.isInteger(parseFloat(value))) {
				error.innerHTML = `Input value '${value}' is not valid. Input must be an Integer.`
				error.style.visibility = "visible"
				return true
			} else {
				error.style.visibility = "hidden"
				return false
			}
		}

		const error2 = (value) => {
			const error = document.getElementById("error2")
			if (value === "") {
				error.innerHTML = `Input value '${value}' is not valid. No input given.`
				error.style.visibility = "visible"
				return true
			} else if (value < 10 || value > 10000 ) {
				error.innerHTML = `Input value '${value}' is not valid. Input must be >= 1 and <= 100000.`
				error.style.visibility = "visible"
				return ture
			} else if (isNaN(value) || !Number.isInteger(parseFloat(value))) {
				error.innerHTML = `Input value '${value}' is not valid. Input must be an Integer.`
				error.style.visibility = "visible"
				return true
			} else {
				error.style.visibility = "hidden"
				document.getElementsByClassName("histogram")[0].style.visibility = "visible"
				return false
			}
		}

		const flipCoins = (coins, times, frequency) => {
			for (let i = 0; i < times; i++) {
				let numHeads = flipCoinsOneTime(coins);
				frequency[numHeads] = frequency[numHeads] + 1;
			}
		}

		const flipCoinsOneTime = (coins) => {
			let heads = 0
			for(let i = 0; i < coins; i++) {
				heads += Math.floor(Math.random()*2);
			}
			return heads
		}

		const printHistogram = (coins, times, frequency) => {
			const classes = Array("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
			for (let i = 0; i < frequency.length; i++) {
				let num = Math.round((frequency[i] / times) * 100)
				let heads = document.getElementsByClassName(classes[i])[0]
				let headTimes = document.getElementsByClassName(classes[i])[1]
				let bar = document.getElementsByClassName(classes[i])[2]
				heads.innerHTML = `${i + 1}`
				headTimes.innerHTML = `${frequency[i]}`
				bar.setAttribute("value", num)
				heads.style.visibility = "visible"
				headTimes.style.visibility = "visible"
				bar.style.visibility = "visible"
			}
		}

		const inputs = getInputs()

		const e1 = error1(inputs[0])
		const e2 = error2(inputs[1])

		if (e1 || e2) {
			document.getElementsByClassName("histogram")[0].style.visibility = "hidden"
		} else {
			document.getElementsByClassName("histogram")[0].style.visibility = "visible"
		}

		const numberOfCoins = parseFloat(inputs[0])
		const numberOfRepitions = parseFloat(inputs[1])


		// Initialize array of zeros
		let frequency = Array(numberOfCoins+1).fill(0)

		// Start timing
		const startTime = performance.now()
		flipCoins(numberOfCoins, numberOfRepitions, frequency);
		// Finish timing
		const executionTime = performance.now() - startTime
		printHistogram(numberOfCoins, numberOfRepitions, frequency);
		console.log("Coin Flipper Time: " + executionTime + "ms");
	}
}