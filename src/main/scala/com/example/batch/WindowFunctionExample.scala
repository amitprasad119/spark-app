package com.example.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object WindowFunctionExample extends App {

  val spark = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("Window Function example")
    .getOrCreate()
  import spark.implicits._

  val employeeDF = Seq(
    ("Petter", 2000, "developer"),
    ("Ian", 4000, "developer"),
    ("Preet", 6000, "developer"),
    ("Michel", 8000, "developer"),
    ("Stacky", 8000, "developer"),
    ("Muthu", 3000, "sales"),
    ("John", 4000, "sales"),
    ("Macky", 5000, "sales"),
    ("William", 7000, "sales"),
    ("Scott", 3000, "Finance"),
    ("Morisson", 4000, "Finance"),
    ("David", 2500, "Finance"),
    ("Jospeh", 1000, "Finance"),
    ("Milan", 2200, "Finance"),
    ("Yun", 2200, "Finance"),
    ("Wokcs", 2200, "Finance")
  ).toDF("Name", "Salary", "Department")

  /**
     Max and min salary department wise
    */
  val winPartByDept = Window.partitionBy("Department")

  val maxMinSalaryDF = employeeDF
    .withColumn("max_salary", max(col("Salary")).over(winPartByDept))
    .withColumn("min_salary", min("Salary").over(winPartByDept))
  // maxMinSalaryDF.show()

  /**
   rank() and dense_rank()
    */
  val rankExprOrderBy = winPartByDept.orderBy(col("salary").desc_nulls_last)
  val rankByDeptDF = employeeDF.withColumn("rank_", rank().over(rankExprOrderBy))
  // rankByDeptDF.show()
  // rankByDeptDF.where(col("rank_") === 1).drop("rank_").show() // this is for top paid per dept

  val denseRankDeptDF = employeeDF.withColumn("rank_", dense_rank().over(rankExprOrderBy))
  // denseRankDeptDF.show()

  /**
    * Running total
    */
  val runningTotalExpr =
    Window
      .partitionBy(col("Department"))
      .orderBy(col("Salary"))
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

  val runningTotalDF =
    employeeDF.withColumn("running_total", sum(col("Salary")).over(runningTotalExpr))
  // runningTotalDF.show()

}
