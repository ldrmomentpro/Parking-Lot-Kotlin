package parking

class Parking {
    data class Car(val plate: String, val color: String)
    data class Spot(val spotNumber: Int, val isFree: Boolean, val car: Car?)

    private lateinit var parkingList: Array<Spot>

    fun park(car: Car) {
        val firstFree = parkingList.first() { it.isFree }
        parkingList[firstFree.spotNumber - 1] = Spot(firstFree.spotNumber, false, car)
        println("${car.color} car parked in spot ${firstFree.spotNumber}.")
    }

    fun leave(spotNumber: Int) {
        if (parkingList[spotNumber - 1].isFree) {
            println("There is no car in spot $spotNumber.")
        } else {
            println("Spot $spotNumber is free.")
            parkingList[spotNumber - 1] = Spot(spotNumber, true, null)
        }
    }

    fun create(size: Int) {
        parkingList = Array(size) { Spot(it + 1, true, null) }
        println("Created a parking lot with $size spots.")
    }

    fun printStatus() {
        val parkingCars = parkingList.filter { !it.isFree }
        if (parkingCars.isEmpty()) {
            println("Parking lot is empty.")
        } else {
            for (i in parkingCars.indices) {
                println("${parkingCars[i].spotNumber} ${parkingCars[i].car?.plate} ${parkingCars[i].car?.color}")
            }
        }
    }

    fun regByColor(color: String) {
        val parkingColors = parkingList.filter { it.car?.color?.lowercase() == color.lowercase() }
        if (parkingColors.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            for (i in parkingColors.indices) {
                print("${parkingColors[i].car?.plate}")
                if (i < parkingColors.size - 1) {
                    print(", ")
                }
            }
            println()
        }
    }

    fun spotByColor(color: String) {
        val parkingColors = parkingList.filter { it.car?.color?.lowercase() == color.lowercase() }
        if (parkingColors.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            for (i in parkingColors.indices) {
                print("${parkingColors[i].spotNumber}")
                if (i < parkingColors.size - 1) {
                    print(", ")
                }
            }
            println()
        }
    }

    fun spotByReg(regNumber: String) {
        val parkingColors = parkingList.filter { it.car?.plate == regNumber }
        if (parkingColors.isEmpty()) {
            println("No cars with registration number $regNumber were found.")
        } else {
            println("${parkingColors.first().spotNumber}")
        }
    }
}

fun main() {
    val parking = Parking()
    while (true) {
        try {
            val input = readLine()!!.split(" ")
            when (input[0]) {
                "create" -> parking.create(input[1].toInt())
                "status" -> parking.printStatus()
                "park" -> parking.park(Parking.Car(input[1], input[2]))
                "leave" -> parking.leave(input[1].toInt())
                "spot_by_color" -> parking.spotByColor(input[1])
                "reg_by_color" -> parking.regByColor(input[1])
                "spot_by_reg" -> parking.spotByReg(input[1])
                "exit" -> break
            }
        } catch (e: NoSuchElementException) {
            println("Sorry, the parking lot is full.")
        } catch (e: Exception) {
            println("Sorry, a parking lot has not been created.")
        }
    }
}
