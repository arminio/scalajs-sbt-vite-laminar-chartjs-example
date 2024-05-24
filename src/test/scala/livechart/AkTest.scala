package livechart

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class AkTest extends AnyFunSuite {



  test("chicken1") {
    val item = DataItem(DataItemID(), "test", 0.5, 5)
    item.fullPrice should be (2.5)
  }


  test("chicken2") {
    val item = DataItem(DataItemID(), "test", 0.5, 5)
    item.fullPrice should be (2.5)
  }

  test("addDataItem") {
    val model = new Model

    val item = DataItem(DataItemID(), "test", 0.5, 2)
    model.addDataItem(item)

    val afterItems = model.dataSignal.now()
    afterItems.size should be (2)
    afterItems.last shouldBe(item)
  }

  test("removeDataItem") {
    val model = new Model

    model.addDataItem(DataItem(DataItemID(), "test", 0.5, 2))

    val beforeItems = model.dataSignal.now()
    beforeItems.size should be (2)

    model.removeDataItem(beforeItems.head.id)

    val afterItems = model.dataSignal.now()
    afterItems.size should be (1)
    afterItems should be (beforeItems.tail)
  }
}