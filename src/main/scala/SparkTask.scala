import org.apache.spark.sql.SparkSession

object SparkTask {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Double Numbers")
      .master("local[*]")
      .getOrCreate()

    val numbersRDD = spark.sparkContext.parallelize(Seq(1, 2, 3, 4, 5,6,7,8,9,10))
    val doubledRDD = numbersRDD.map(_ * 2)

    doubledRDD.collect().foreach(println)

    doubledRDD.mapPartitionsWithIndex {
      case (index, iterator) => {
        println(s"Partition $index: ${iterator.mkString(", ")}")
        iterator
      }
    }.count()
    spark.stop()
  }
}
