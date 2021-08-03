package eu.assignment.project.erate.service;

import eu.assignment.project.erate.common.gen.api.ApiApi;
import eu.assignment.project.erate.common.gen.api.ApiApiDelegate;
import eu.assignment.project.erate.common.gen.api.ApiUtil;
import eu.assignment.project.erate.common.gen.model.ArrayData;
import eu.assignment.project.erate.common.gen.model.ArrayRequestData;
import eu.assignment.project.erate.common.gen.model.DividedArrayData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ArrayOperationService implements ApiApiDelegate {

    default ResponseEntity<ArrayData> addToArray(ArrayRequestData arrayRequestData) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ 0, 0 ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/v1/subDivide : This API will check whether the array can be split in two, without reordering the elements, so that the sum of the two resulting arrays is equal.
     *
     * @return Successful response: contents of the Array. (status code 200)
     *         or Unexpected error (status code 200)
     * @see ApiApi#divideArray
     */
    default ResponseEntity<DividedArrayData> divideArray() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ [ 0, 0 ], [ 0, 0 ] ], \"status\" : \"status\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/v1/list : This API will return the contents of the array.
     *
     * @return Successful response: contents of the Array. (status code 200)
     *         or Unexpected error (status code 200)
     * @see ApiApi#listArray
     */
    default ResponseEntity<ArrayData> listArray() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ 0, 0 ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }
}
