//package livechart.tonic
//
//import com.raquo.laminar.api.L
//import com.raquo.laminar.nodes.ReactiveHtmlElement
//
//import org.scalajs.dom.KeyCode.Enter
//import org.scalajs.dom._
//import org.scalajs.dom.html.Element
//
//import java.net.URI
//import scala.concurrent.{ExecutionContext, Future, Promise}
//import scala.scalajs.js
//
//trait CommonTestPageFunctions {
//
//  implicit class NodeListSeq[T <: Node](nodes: DOMList[T]) extends IndexedSeq[T] {
//    override def foreach[U](f: T => U): Unit =
//      for (i <- 0 until nodes.length) {
//        f(nodes(i))
//      }
//
//    override def length: Int = nodes.length
//
//    override def apply(idx: Int): T = nodes(idx)
//  }
//
//  def makePage(annotationStorage: AnnotationFrontendService, url: String): ReactiveHtmlElement[Element] = {
//    val annotationDetails = annotationRoute(url)
//    val stream: L.EventStream[Either[String, AnnotationWrapper]] = annotationStorage.getAnnotations[AnnotationWrapper](annotationDetails)
//    AnnotationPage.renderPage(stream)
//  }
//
//  protected def annotationRoute(url: String): AnnotationsRoute = {
//    val errorOrDocumentRef = UrlRoute.parse(new URI(url).getFragment)
//    val annotationDetails = errorOrDocumentRef.fold(e => throw new RuntimeException(e), _.asInstanceOf[AnnotationsRoute])
//    annotationDetails
//  }
//
//  def indexNameInputValue() = inputValue("indexName")
//
//  def md5InputValue() = inputValue("documentMd5")
//
//  def serviceNameInputValue() = inputValue("serviceName")
//
//  def userIdInputValue() = inputValue("userId")
//
//  def provideInputValueForUserId(str: String) =
//    provideInputValue(getElementById[HTMLInputElement]("userId"), str)
//
//  def commentInputValue() =
//    getElementById[HTMLTextAreaElement]("comments")
//      .map(_.value)
//
//  def provideInputValueForComments(str: String): Option[Boolean] =
//    provideTextAreaValue(getElementById[HTMLTextAreaElement]("comments"), str)
//
//  def isSaveButtonDisabled() =
//    getElementById[HTMLInputElement]("saveButton")
//      .getOrElse(throw new Exception("HTML element not found"))
//      .disabled
//
//  def getSuccessResultAlert() =
//    getElementById[HTMLDivElement]("successResultAlert")
//
//  def getFailResultAlert() =
//    getElementById[HTMLDivElement]("failResultAlert")
//
//  def inputValue(elementId: String) =
//    getElementById[HTMLInputElement](elementId)
//      .map(_.value)
//
//  def isPageReady() = {
//    val saveButton =
//      Option(
//        document
//          .getElementById("saveButton"))
//
//    saveButton.isDefined
//  }
//
//  def getElementById[T](id: String): Option[T] =
//    Option(
//      document
//        .getElementById(id))
//      .map(_.asInstanceOf[T])
//
//  def waitForPageReady()(implicit executionContext: ExecutionContext): Future[Unit] =
//    waitUntil(isPageReady())
//
//  def waitUntil(f: => Boolean, count: Int = 10)(implicit executionContext: ExecutionContext): Future[Unit] =
//    if (count == 0)
//      Future.failed(new RuntimeException("Counter reached zero but the condition never met!"))
//    else if (f)
//      Future.successful(())
//    else
//      delay(500)
//        .flatMap(_ => waitUntil(f, count - 1))
//
//  def failResultAlertVisible(): Boolean =
//    getElementById[HTMLDivElement]("failResultAlert").isDefined
//
//  def successfulResultAlertVisible(): Boolean =
//    getElementById[HTMLDivElement]("successResultAlert").isDefined
//
//  def clickSaveButton() =
//    clickButton("saveButton")
//
//  def clickButton(id: String) =
//    getElementById[HTMLButtonElement](id).map(
//      b => b.click()
//    )
//
//  def provideInputValue(maybeElement: Option[HTMLInputElement], str: String) =
//    maybeElement
//      .map { elem =>
//        elem.value = str
//        elem.dispatchEvent(
//          new Event(
//            "input",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  def pressEnterOnInput(maybeElement: Option[HTMLInputElement]) =
//    maybeElement
//      .map { elem =>
//        elem.dispatchEvent(new KeyboardEvent("keypress", new KeyboardEventInit {
//          keyCode = Enter
//          //          key = "Enter"
//        }))
//      }
//
//  def blurInput(maybeElement: Option[HTMLInputElement]) =
//    maybeElement
//      .map { elem =>
//        elem.dispatchEvent(
//          new Event(
//            "blur",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  def doubleClickElement(maybeElement: Option[HTMLElement]) =
//    maybeElement
//      .map { elem =>
//        elem.dispatchEvent(
//          new Event(
//            "dblclick",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  def provideCheckedValue(maybeElement: Option[HTMLInputElement], checked: Boolean) =
//    maybeElement
//      .map { elem =>
//        elem.checked = checked
//        elem.dispatchEvent(
//          new Event(
//            "click",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  def clickRadioButton(maybeElement: Option[HTMLInputElement]) =
//    maybeElement
//      .map { elem =>
//        elem.dispatchEvent(
//          new Event(
//            "click",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  def provideSelectValue(maybeElement: Option[HTMLInputElement], str: String) =
//    maybeElement
//      .map { elem =>
//        elem.value = str
//        elem.dispatchEvent(
//          new Event(
//            "change",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  def provideTextAreaValue(maybeElement: Option[HTMLTextAreaElement], str: String) =
//    maybeElement
//      .map { elem =>
//        elem.value = str
//        elem.dispatchEvent(
//          new Event(
//            "input",
//            new EventInit {
//              bubbles = true
//            }
//          ))
//      }
//
//  protected def delay(milliseconds: Int): Future[Unit] = {
//    val p = Promise[Unit]()
//    js.timers.setTimeout(milliseconds) {
//      println(s"delay(): in the timer of $milliseconds milliseconds.")
//      p.success(())
//    }
//    p.future
//  }
//
//}
