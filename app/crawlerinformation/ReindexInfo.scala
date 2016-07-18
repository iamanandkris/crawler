package crawlerinformation

import play.api.libs.json.Json

/**
  * Created by AnandKrishnan on 7/16/2016.
  */
case class ReindexInfo (url:String, search_item:String, alreadyDone: String)

object ReindexInfo{
  implicit val reads = Json.reads[ReindexInfo]
  implicit val writes = Json.writes[ReindexInfo]
}

