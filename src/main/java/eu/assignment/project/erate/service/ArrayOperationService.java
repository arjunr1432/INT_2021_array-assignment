package eu.assignment.project.erate.service;

import eu.assignment.project.erate.common.gen.api.ApiApiDelegate;
import eu.assignment.project.erate.common.gen.model.ArrayData;
import eu.assignment.project.erate.common.gen.model.ArrayRequestData;
import eu.assignment.project.erate.common.gen.model.DeleteArrayData;
import eu.assignment.project.erate.common.gen.model.DividedArrayData;
import eu.assignment.project.erate.exception.CustomBusinessException;
import eu.assignment.project.erate.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArrayOperationService implements ApiApiDelegate {

    private final RepositoryService mongoDbRepositoryService;

    @Override
    public ResponseEntity<ArrayData> addToArray(ArrayRequestData arrayRequestData) {
        List<Integer> arrayElements = new ArrayList<Integer>();
        try {
            mongoDbRepositoryService.addElement(arrayRequestData.getElement());
            arrayElements = mongoDbRepositoryService.listArray();
        } catch (Exception e) {
            log.error("Exception while accessing data from database.");
            throw new CustomBusinessException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exception while trying to add a new array content.");}
        return new ResponseEntity<>(new ArrayData().data(arrayElements), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<DividedArrayData> divideArray() {
        List<List<Integer>> responseList = new ArrayList<>();
        List<Integer> arrayElements = new ArrayList<Integer>();
        DividedArrayData responseData = new DividedArrayData();
        try {
            arrayElements = mongoDbRepositoryService.listArray();
            if (arrayElements.size() != 0) {
                int arraySum = findSum(arrayElements);
                int sumThreshold = arraySum / 2;
                boolean isSplitSuccess = false;
                if (arraySum % 2 == 0) {
                    for (int i = 0, sum = 0; i < arrayElements.size() && sum < sumThreshold; i++) {
                        sum += arrayElements.get(i);
                        if (sum == sumThreshold) {
                            isSplitSuccess = true;
                            responseList.add(arrayElements.subList(0, i+1));
                            responseList.add(arrayElements.subList(i+1, arrayElements.size()));
                            responseData.setData(responseList);
                            responseData.setStatus("Successfully split in to two.");
                        }
                    }
                    if (!isSplitSuccess) {
                        responseData.setStatus("Not possible to split.");
                    }
                } else {
                    responseData.setStatus("Not possible to split.");
                }
            } else {
                responseData.setStatus("Empty array, not possible to split.");
            }
        } catch (Exception e) {
            log.error("Exception while accessing data from database.");
            throw new CustomBusinessException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exception while trying to spit array content.");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArrayData> listArray() {
        List<Integer> arrayElements = new ArrayList<Integer>();
        try {
            arrayElements = mongoDbRepositoryService.listArray();
        } catch (Exception e) {
            log.error("Exception while accessing data from database.");
            throw new CustomBusinessException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exception while trying to access array content.");
        }
        return new ResponseEntity<>(new ArrayData().data(arrayElements), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<DeleteArrayData> emptyArray() {
        try {
            mongoDbRepositoryService.emptyArray();
        } catch (Exception e) {
            log.error("Exception while accessing data from database.");
            throw new CustomBusinessException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exception while trying to clear array content.");
        }
        return new ResponseEntity<>(new DeleteArrayData().message("Array cleared successfully."), HttpStatus.OK);

    }

    private int findSum(List<Integer> array) {
        return array.stream().mapToInt(value -> value).sum();
    }
}
