package controllers

import play.api.libs.json.Json
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import utils.FileUtils.{getStreamFromFile, handleFilePartAsFile}

import java.io.File
import java.nio.file.Files
import javax.inject._
import scala.collection.immutable.ListMap

class WordsController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private def countWords(lines: LazyList[String]) = {
    ListMap.from(
      lines.flatMap(line => "(?i)[a-z]+".r.findAllIn(line.toLowerCase()))
        .groupMap(identity)(_ => 1)
        .map(entry => (entry._1, entry._2.sum))
        .toList
        .sortBy(_._2).reverse
    )
  }

  def calcWordList(): Action[MultipartFormData[File]] = Action(parse.multipartFormData(handleFilePartAsFile)) { implicit request =>
    val fileOption = request.body.file("file").map {
      case FilePart(_, _, _, file, _, _) =>
        val linesLazyList = getStreamFromFile(file)
        Files.deleteIfExists(file.toPath)
        Json.toJson(countWords(linesLazyList))
    }
    Ok(s"${fileOption.getOrElse("File not uploaded")}").as("application/json")
  }

}
