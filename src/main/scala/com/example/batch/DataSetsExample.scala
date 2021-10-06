package com.example.batch

import org.apache.spark.sql.SparkSession

object DataSetsExample extends App {

  val spark = SparkSession
    .builder()
    .appName("Data set example")
    .config("spark.master", "local")
    .getOrCreate()

  val carsDF = spark.read.json("src/resources/data/cars.json")
  // carsDF.show()

  case class Cars(
    Acceleration: Option[Double],
    Cylinders: Option[Double],
    Horsepower: Option[Long],
    Name: String)
  import spark.implicits._
  val carsDS = carsDF.as[Cars]
  println("Total Cars:" + carsDS.count())
  def add(a: Long, b: Long) = a + b
  val powerfulCars = carsDS.filter(_.Horsepower.fold(false)(_ > 140))
  val averageHp = carsDS.map(_.Horsepower.fold(0L)(_.toLong)).reduce((l, l1) => add(l, l1))
  println("Total powerful cars: " + powerfulCars.count())
  println("Total avg Hp cars :" + averageHp / carsDS.count())

}
