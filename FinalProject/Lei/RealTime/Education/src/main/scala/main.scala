import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Score Statistics")
      .config("spark.master", "local")
      .getOrCreate()
//    elegant_method(spark)
    common_method(spark)
  }

  def common_method(spark: SparkSession): Unit = {
    // 2.1 RDD 存储文件信息
    val scoreRDD = spark.sparkContext.textFile("../data.txt")

    // 2.2 RDD + Schema 组成 DataFrame
    val rowRDD = scoreRDD
      .map(_.split(","))
      .map(attributes => Row(attributes(0), attributes(1), attributes(2), attributes(3)))
    var scoreDF = spark.createDataFrame(rowRDD, get_schema("kch,kcmc,bfzcj,type"))

    import spark.implicits._
    // 3.1 计算平均成绩
    scoreDF = scoreDF.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc)
    val avg_score = scoreDF.groupBy("type").avg("bfzcj")

    // 3.2 利用转换得到专业课的平均成绩和专业课信息，并将上述两个信息存入MySQL表
    df2mysql(avg_score.filter($"type" === "专业课"), "prof_avg_score")
    df2mysql(scoreDF.filter($"type" === "专业课"), "yokey_zcst_score")
  }


  // 通过应用程序添加表头用的表头结构 (辅助2.实现)
  def get_schema(schemaString: String): StructType = {
//    val schemaString = "kch,kcmc,bfzcj,type"
    // Generate the schema based on the string of schema
    val fields = schemaString.split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    StructType(fields)
  }

  def df2mysql(df: DataFrame, tabel_name: String): Unit = {
    df.write.format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://localhost:3306/score")
      .option("dbtable", tabel_name)
      .option("user", "root")
      .mode("overwrite")
      .save()
  } //.option("password", "toor")

  // * Spark SQL 简洁实现
  def elegant_method(spark: SparkSession): Unit = {

    // 利用 Spark SQL 一键读取，过于简洁！！！
    var df = spark.read.option("header", "false").csv("../data.txt")
    df = df.toDF("kch","kcmc","bfzcj","type")

    import spark.implicits._ // This import is needed to use the $-notation
    df = df.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc)

    // 求平均成绩
    println("平均成绩：")
    df.groupBy("type").avg("bfzcj").show()

    // 专业课的成绩信息到MySQL中
    df2mysql(df.filter($"type" === "专业课"), "yokey_zcst_score")
  }

}
