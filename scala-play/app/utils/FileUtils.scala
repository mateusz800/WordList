package utils

import akka.stream.IOResult
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.libs.streams._
import play.api.mvc.MultipartFormData.FilePart
import play.core.parsers.Multipart.FileInfo

import java.io.{BufferedReader, File, FileReader}
import java.nio.file.{Files, Path}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.StreamConverters._

object FileUtils {

  type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

  def handleFilePartAsFile: FilePartHandler[File] = {
    case FileInfo(partName, filename, contentType, _) =>
      val path: Path = Files.createTempFile("multipartBody", "tempFile")
      val fileSink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(path)
      val accumulator: Accumulator[ByteString, IOResult] = Accumulator(fileSink)
      accumulator.map {
        case IOResult(_, _) =>
          FilePart(partName, filename, contentType, path.toFile)
      }
  }

  def getStreamFromFile(file: File): LazyList[String] = {
    new BufferedReader(new FileReader(file)).lines().toScala(LazyList)
  }
}
