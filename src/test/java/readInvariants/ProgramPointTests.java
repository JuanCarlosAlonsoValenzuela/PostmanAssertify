package readInvariants;

import agora.postman.assertion.model.PptType;
import agora.postman.assertion.model.ProgramPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


/**
 * @author Juan C. Alonso
 */
public class ProgramPointTests {

    /*
     *  TODO: %array (one and multiple)
     */

    // EXIT WITHOUT nexting level
//    @Test
//    public void testCreateProgramPointWithoutNextingLevel() {
//        ProgramPoint programPoint = new ProgramPoint("/businesses/search&getBusinesses&200():::EXIT");
//
//        Assertions.assertEquals(PptType.EXIT, programPoint.getPptType(), "Incorrect PptType value");
//        Assertions.assertEquals("/businesses/search", programPoint.getEndpoint(), "Unexpected endpoint value");
//        Assertions.assertEquals("getBusinesses", programPoint.getOperationId(), "Unexpected operation id value");
//        Assertions.assertEquals(200, programPoint.getResponseCode(), "Unexpected response code value");
//        Assertions.assertEquals(0, programPoint.getVariableHierarchy().size(), "Unexpected variable hierarchy size");
//
//    }

    // ENTER with MULTIPLE nesting levels
//    @Test
//    public void testCreateEnterProgramPoint() {
//        ProgramPoint programPoint = new ProgramPoint("/videos&search_videos&400&data&user&pictures&sizes():::ENTER");
//
//        Assertions.assertEquals(PptType.ENTER, programPoint.getPptType(), "Incorrect PptType value");
//        Assertions.assertEquals("/videos", programPoint.getEndpoint(), "Unexpected endpoint value");
//        Assertions.assertEquals("search_videos", programPoint.getOperationId(), "Unexpected operation id value");
//        Assertions.assertEquals(400, programPoint.getResponseCode(), "Unexpected response code value");
//
//        List<String> expectedVariableHierarchy = List.of("data", "user", "pictures", "sizes");
//        List<String> returnedVariableHierarchy = programPoint.getVariableHierarchy();
//
//        Assertions.assertEquals(4, returnedVariableHierarchy.size(), "Unexpected variable hierarchy size");
//
//        for(int i=0; i<expectedVariableHierarchy.size(); i++) {
//            Assertions.assertEquals(expectedVariableHierarchy.get(i), returnedVariableHierarchy.get(i), "Unexpected variable value");
//        }
//
//    }

    // EXIT with ONE nesting level
//    @Test
//    public void testCreateProgramPointWithOneNestingLevel() {
//        ProgramPoint programPoint = new ProgramPoint("/albums/{id}/tracks&getAlbumTracks&200&items():::EXIT");
//
//        Assertions.assertEquals(PptType.EXIT, programPoint.getPptType(), "Incorrect PptType value");
//        Assertions.assertEquals("/albums/{id}/tracks", programPoint.getEndpoint(), "Unexpected endpoint value");
//        Assertions.assertEquals("getAlbumTracks", programPoint.getOperationId(), "Unexpected operation id value");
//        Assertions.assertEquals(200, programPoint.getResponseCode(), "Unexpected response code value");
//        Assertions.assertEquals(1, programPoint.getVariableHierarchy().size(), "Unexpected variable hierarchy size");
//        Assertions.assertEquals("items", programPoint.getVariableHierarchy().get(0), "Unexpected variable value");
//
//    }
}
