package eu.assignment.project.erate.controller;

import eu.assignment.project.erate.common.gen.api.ApiApi;
import eu.assignment.project.erate.common.gen.model.ArrayData;
import eu.assignment.project.erate.common.gen.model.ArrayRequestData;
import eu.assignment.project.erate.common.gen.model.DividedArrayData;
import eu.assignment.project.erate.common.gen.model.Error;
import eu.assignment.project.erate.models.RequestBodyData;
import eu.assignment.project.erate.repository.RepositoryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/")
public class ArrayOperationController implements ApiApi {

    @Autowired
    private RepositoryService mongoDbRepositoryService;

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
            @ApiResponse(code = 400, message = "Failed response: Bad request", response = Error.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = Error.class) })
    @PostMapping(
            value = "/api/v1/addElement",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<ArrayData> addToArray(@ApiParam(value = "Request payload for adding a new element to the existing Array." ,required=true )  @Valid @RequestBody ArrayRequestData arrayRequestData) {
        return getDelegate().addToArray(arrayRequestData);
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
            @ApiResponse(code = 200, message = "Successful response: contents of the Array.", response = DividedArrayData.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = Error.class) })
    @GetMapping(
            value = "/api/v1/subDivide",
            produces = { "application/json" }
    )
    @Override
    public ResponseEntity<DividedArrayData> divideArray() {
        return getDelegate().divideArray();
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
            @ApiResponse(code = 200, message = "Successful response: contents of the Array.", response = ArrayData.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = Error.class) })
    @GetMapping(
            value = "/api/v1/list",
            produces = { "application/json" }
    )
    @Override
    public ResponseEntity<ArrayData> listArray() {
        return getDelegate().listArray();
    }

    @GetMapping(path = "/list")
    public ResponseEntity<String> testAPI(HttpServletRequest request) {
        return new ResponseEntity<>(mongoDbRepositoryService.listArray().toString(), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/addElement")
    public ResponseEntity<String> testAPI2(HttpServletRequest request, @RequestBody RequestBodyData data) {
        mongoDbRepositoryService.addElement(data.getData());
        return new ResponseEntity<>("Success",HttpStatus.ACCEPTED);
    }
}
