package byow.Core.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestCore {
    public static void main(String[] args) {

        Class<?>[] testClasses = new Class<?>[] {
            TestBridgeBuilder.class,
            TestDedupPQ.class,
            TestDirection.class,
            TestEdge.class,
            TestEngine.class,
            TestGameData.class,
            TestGameEntity.class,
            TestGame.class,
            TestGrid.class,
            TestInputProcessor.class,
            TestMazeBuilder.class,
            TestNavigableTileGrid.class,
            TestPointGraph.class,
            TestPoint.class,
            TestStringInputSource.class,
            TestTileGrid.class,
            TestUnionFind.class
        };

        //Result result = JUnitCore.runClasses(testClasses);
        //System.out.println(TestPoint.class.getName());
        //for (Failure failure : result.getFailures()) {
        //    System.out.println(failure.toString());
        //}
        //System.out.println(result.wasSuccessful());

        jh61b.junit.TestRunner.runTests("all", testClasses);
    }
}
