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
      .map(_.split(",")) // 将 csv 文件中每一行按逗号分隔
      .map(attributes => Row(attributes(0), attributes(1), attributes(2), attributes(3))) // 将分隔后的元素转换为行对象，总共有4个元素
    var scoreDF = spark.createDataFrame(rowRDD, get_schema("kch,kcmc,bfzcj,type")) // 创建表头并组成DataFrame

    import spark.implicits._
    // 3.1 计算平均成绩
    scoreDF = scoreDF.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc) // 将成绩由字符串转换为整型方便计算，并按成绩降序排序
    val avg_score = scoreDF.groupBy("type").avg("bfzcj") // 计算每种课程的平均成绩（虽然最后只展示专业课的）

    // 3.2 利用转换得到专业课的平均成绩和专业课信息，并将上述两个信息存入MySQL表
    df2mysql(avg_score.filter($"type" === "专业课"), "prof_avg_score") // 将平均成绩存入MySQL表
    df2mysql(scoreDF.filter($"type" === "专业课"), "yokey_zcst_score") // 将专业课成绩详情存入MySQL表
  }


  // 通过应用程序添加表头用的表头结构 (辅助2.实现)
  def get_schema(schemaString: String): StructType = {
    val fields = schemaString.split(",") // 提供的表头会用逗号分隔，先把它们切开
      .map(fieldName => StructField(fieldName, StringType, nullable = true)) // 将字符串转换为表头结构
    StructType(fields)
  }

  // 将 DataFrame 存入指定的 MySQL 表中 (辅助3.实现)
  def df2mysql(df: DataFrame, tabel_name: String): Unit = {
    df.write.format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver") // mysql 驱动，在 pom.xml 中指定版本，默认为 8.0.29 ！！！
      .option("url", "jdbc:mysql://localhost:3306/score") // 数据库地址，需要按照自己的情况修改
      .option("dbtable", tabel_name)
      .option("user", "root")
      .mode("overwrite") // 如果表存在，则覆盖
      .save()
    //如果mysql数据库有密码，可以在上面添加，本处默认root无密码 `.option("password", "toor")`
  }

  // * Spark SQL 简洁实现 （仅展示，无评分细则对应）
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
