package eu.assignment.project.erate.controller;

import eu.assignment.project.erate.common.gen.api.ApiApi;
import eu.assignment.project.erate.common.gen.model.Error;
import eu.assignment.project.erate.common.gen.model.*;
import eu.assignment.project.erate.service.ArrayOperationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class ArrayOperationController implements ApiApi {

    private final ArrayOperationService arrayOperationService;

    /**
     * POST /api/v1/addElement : This API will add a new element to the existing Array.
     *
     * @param arrayRequestData Request payload for adding a new element to the existing Array. (required)
     * @return Successful response: contents of the Array after adding the new element. (status code 200)
     *         or Failed response: Bad request (status code 400)
     *         or Unexpected error (status code 200)
     */
    @ApiOperation(value = "This API will add a new element to the existing Array.", nickname = "addToArray", notes = "", response = ArrayData.class, authorizations = {

            @Authorization(value = "BasicAuth")
    }, tags={ "Array Operations", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response: contents of the Array after adding the new element.", response = ArrayData.class),
            @ApiResponse(code = 400, message = "Failed response: Bad request", response = Error.class)})
    @PostMapping(
            value = "/api/v1/addElement",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<ArrayData> addToArray(@ApiParam(value = "Request payload for adding a new element to the existing Array." ,required=true )  @Valid @RequestBody ArrayRequestData arrayRequestData) {
        log.info("Request received for adding new element to array, element={}", arrayRequestData.getElement());
        return arrayOperationService.addToArray(arrayRequestData);
    }


    /**
     * GET /api/v1/subDivide : This API will check whether the array can be split in two, without reordering the elements, so that the sum of the two resulting arrays is equal.
     *
     * @return Successful response: contents of the Array. (status code 200)
     *         or Unexpected error (status code 200)
     */
    @ApiOperation(value = "This API will check whether the array can be split in two, without reordering the elements, so that the sum of the two resulting arrays is equal.", nickname = "divideArray", notes = "", response = DividedArrayData.class, authorizations = {

            @Authorization(value = "BasicAuth")
    }, tags={ "Array Operations", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response: contents of the Array.", response = DividedArrayData.class)})
    @GetMapping(
            value = "/api/v1/subDivide",
            produces = { "application/json" }
    )
    @Override
    public ResponseEntity<DividedArrayData> divideArray() {
        log.info("Request received for dividing the arrays to two.");
        return arrayOperationService.divideArray();
    }


    /**
     * GET /api/v1/list : This API will return the contents of the array.
     *
     * @return Successful response: contents of the Array. (status code 200)
     *         or Unexpected error (status code 200)
     */
    @ApiOperation(value = "This API will return the contents of the array.", nickname = "listArray", notes = "", response = ArrayData.class, authorizations = {

            @Authorization(value = "BasicAuth")
    }, tags={ "Array Operations", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response: contents of the Array.", response = ArrayData.class)})
    @GetMapping(
            value = "/api/v1/list",
            produces = { "application/json" }
    )
    @Override
    public ResponseEntity<ArrayData> listArray() {
        log.info("Request received for listing the array");
        return arrayOperationService.listArray();
    }

    /**
     * DELETE /api/v1/deleteArray : This API will clear the existing Array.
     *
     * @return Successfull response: status of array deletion. (status code 200)
     */
    @ApiOperation(value = "This API will clear the existing Array.", nickname = "emptyArray", notes = "", response = DeleteArrayData.class, authorizations = {

            @Authorization(value = "BasicAuth")
    }, tags={ "Array Operations", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response: status of array deletion.", response = DeleteArrayData.class) })
    @DeleteMapping(
            value = "/api/v1/deleteArray",
            produces = { "application/json" }
    )
    public ResponseEntity<DeleteArrayData> emptyArray() {
        log.info("Request received for deleting the array");
        return arrayOperationService.emptyArray();
    }

}
