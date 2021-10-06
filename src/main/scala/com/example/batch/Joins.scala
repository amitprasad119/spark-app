package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object Joins extends App {

  val spark = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("Joins Operations")
    .getOrCreate()

  val bandsDF = spark.read
    .json("src/resources/data/bands.json")

  bandsDF.show()

  val guitaristDF = spark.read
    .json("src/resources/data/guitarPlayers.json")
  guitaristDF.show()

  /**
     Inner joins
    */
  val joinExpr = col("band") === col("bandId")

  val guitaristBandInnerJoinsDF = guitaristDF.join(
    bandsDF.withColumnRenamed("id", "bandId"),
    joinExpr,
    "inner"
  ) // default is inner and option to mention

  guitaristBandInnerJoinsDF.show()

  /**
    *  Left outer joins
    */

  val guitaristBandLeftJoinsDF = guitaristDF.join(
    bandsDF.withColumnRenamed("id", "bandId"),
    joinExpr,
    "left_outer"
  )

  guitaristBandLeftJoinsDF.show()

  /**
    *  Right outer joins
    */

  val guitaristBandRightJoinsDF =
    guitaristDF.join(bandsDF.withColumnRenamed("id", "bandId"), joinExpr, "right_outer")

  guitaristBandRightJoinsDF.show()

  /**
    *  semi joins  ( display from left which matched from both the table)
    */

  val guitaristBandSemiJoinsDF =
    guitaristDF.join(bandsDF.withColumnRenamed("id", "bandId"), joinExpr, "left_semi")

  guitaristBandSemiJoinsDF.show()

  /**
    *  Anti joins
    */

  val guitaristBandAntiJoinsDF =
    guitaristDF.join(bandsDF.withColumnRenamed("id", "bandId"), joinExpr, "left_semi")

  guitaristBandSemiJoinsDF.show()

}
