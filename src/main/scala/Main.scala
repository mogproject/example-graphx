import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.storage.StorageLevel
import org.apache.log4j.{Level, Logger}
import scala.io.Source

object Main extends App {
  // Start Spark.
  println("\n### Starting Spark\n")
  val sparkConf = new SparkConf().setAppName("example-graphx").setMaster("local[2]")
  implicit val sc = new SparkContext(sparkConf)

  // Suppress unnecessary logging.
  Logger.getRootLogger.setLevel(Level.ERROR)

  // Load a graph.
  val path = "src/main/resources/edge_list_1.txt"

  println(s"\n### Loading edge list: ${path}\n")
  Source.fromFile(path).getLines().foreach(println)

  val g = GraphLoader.edgeListFile(
    sc,
    path,
    edgeStorageLevel = StorageLevel.MEMORY_AND_DISK,
    vertexStorageLevel = StorageLevel.MEMORY_AND_DISK
  )

  println("\n### Degree centrality\n")
  g.degrees.sortByKey().collect().foreach { case (n, v) => println(s"Node: ${n} -> Degree: ${v}") }

  println("\n### Betweenness centrality\n")
  val h = Betweenness.run(g)
  h.vertices.sortByKey().collect().foreach { case (n, v) => println(s"Node: ${n} -> Betweenness: ${v}") }

  // Stop Spark.
  sc.stop()
}
