package eu.assignment.project.erate.service;

import eu.assignment.project.erate.common.gen.model.ArrayData;
import eu.assignment.project.erate.common.gen.model.ArrayRequestData;
import eu.assignment.project.erate.common.gen.model.DeleteArrayData;
import eu.assignment.project.erate.common.gen.model.DividedArrayData;
import eu.assignment.project.erate.exception.CustomBusinessException;
import eu.assignment.project.erate.repository.RepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArrayOperationServiceTest {

    private RepositoryService mongoDbRepositoryService;
    private ArrayOperationService arrayOperationService;

    @BeforeAll
    public void init() {
        mongoDbRepositoryService = Mockito.mock(RepositoryService.class);
        arrayOperationService = new ArrayOperationService(mongoDbRepositoryService);
    }

    @Test
    void testAddToArraySuccess() {
        ArrayRequestData arrayRequestData = new ArrayRequestData().element(12345);
        Mockito.doNothing().when(mongoDbRepositoryService).addElement(Mockito.eq(12345));
        Mockito.when(mongoDbRepositoryService.listArray()).thenReturn(Arrays.asList(76543, 12345));
        ResponseEntity<ArrayData> responseEntity = arrayOperationService.addToArray(arrayRequestData);

        Assertions.assertEquals(201, responseEntity.getStatusCode().value());
        Assertions.assertEquals(2, responseEntity.getBody().getData().size());
    }

    @Test
    void testAddToArrayException() {
        ArrayRequestData arrayRequestData = new ArrayRequestData().element(12345);
        Mockito.doThrow(new RuntimeException()).when(mongoDbRepositoryService).addElement(Mockito.eq(12345));

        try {
            arrayOperationService.addToArray(arrayRequestData);

        } catch (CustomBusinessException e) {
            Assertions.assertEquals(503, e.getHttpStatus().value());
            Assertions.assertEquals("Exception while trying to add a new array content.", e.getMessage());
        }
    }

    @Test
    void testDivideArray() {
        Mockito.when(mongoDbRepositoryService.listArray()).thenReturn(Arrays.asList(1, 2, 3));
        ResponseEntity<DividedArrayData> responseEntity = arrayOperationService.divideArray();

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals(2, responseEntity.getBody().getData().get(0).size());
        Assertions.assertEquals(1, responseEntity.getBody().getData().get(1).size());
        Assertions.assertEquals("Successfully split in to two.", responseEntity.getBody().getStatus());
    }

    @Test
    void testDivideArray_withEmptyArray() {
        Mockito.when(mongoDbRepositoryService.listArray()).thenReturn(Collections.emptyList());
        ResponseEntity<DividedArrayData> responseEntity = arrayOperationService.divideArray();

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals(null, responseEntity.getBody().getData());
        Assertions.assertEquals("Empty array, not possible to split.", responseEntity.getBody().getStatus());
    }

    @Test
    void testDivideArray_withNonSplittableArray_1() {
        Mockito.when(mongoDbRepositoryService.listArray()).thenReturn(Arrays.asList(12345, 67890));
        ResponseEntity<DividedArrayData> responseEntity = arrayOperationService.divideArray();

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals("Not possible to split.", responseEntity.getBody().getStatus());
    }

    @Test
    void testDivideArray_withNonSplittableArray_2() {
        Mockito.when(mongoDbRepositoryService.listArray()).thenReturn(Arrays.asList(1, 2, 3, 4));
        ResponseEntity<DividedArrayData> responseEntity = arrayOperationService.divideArray();

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals("Not possible to split.", responseEntity.getBody().getStatus());
    }

    @Test
    void testDivideArrayException() {
        Mockito.doThrow(new RuntimeException()).when(mongoDbRepositoryService).emptyArray();
        try {
            arrayOperationService.divideArray();
        } catch (CustomBusinessException e) {
            Assertions.assertEquals(503, e.getHttpStatus().value());
            Assertions.assertEquals("Exception while trying to spit array content.", e.getMessage());
        }
    }

    @Test
    void testListArray() {
        Mockito.when(mongoDbRepositoryService.listArray()).thenReturn(Arrays.asList(12345, 67890, 55555));
        ResponseEntity<ArrayData> responseEntity = arrayOperationService.listArray();

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals(3, responseEntity.getBody().getData().size());
    }

    @Test
    void testListArrayException() {
        Mockito.doThrow(new RuntimeException()).when(mongoDbRepositoryService).listArray();
        try {
            arrayOperationService.listArray();
        } catch (CustomBusinessException e) {
            Assertions.assertEquals(503, e.getHttpStatus().value());
            Assertions.assertEquals("Exception while trying to access array content.", e.getMessage());
        }
    }

    @Test
    void testEmptyArray() {
        Mockito.doNothing().when(mongoDbRepositoryService).emptyArray();
        ResponseEntity<DeleteArrayData> responseEntity = arrayOperationService.emptyArray();

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals("Array cleared successfully.", responseEntity.getBody().getMessage());
    }

    @Test
    void testEmptyArrayException() {
        Mockito.doThrow(new RuntimeException()).when(mongoDbRepositoryService).emptyArray();

        try {
            arrayOperationService.emptyArray();
        } catch (CustomBusinessException e) {
            Assertions.assertEquals(503, e.getHttpStatus().value());
            Assertions.assertEquals("Exception while trying to clear array content.", e.getMessage());
        }
    }
}