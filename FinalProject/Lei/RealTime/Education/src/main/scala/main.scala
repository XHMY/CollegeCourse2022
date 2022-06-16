import org.apache.spark.sql.SparkSession

object main {
  def main(args: Array[String]): Unit = {
    elegant_method()
  }

  def elegant_method(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Score Statistics")
      .config("spark.master", "local")
      .getOrCreate()

    // 利用 Spark SQL 一键读取，过于简洁！！！
    var df = spark.read.option("header", "true").csv("../data.txt")

    // 列 'bfzcj' 为成绩，其它列都不重要
    import spark.implicits._ // This import is needed to use the $-notation
    df = df.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc)
//    val prof_df = df.filter($"type" === "专业课")
//    prof_df.show()
  }
}
