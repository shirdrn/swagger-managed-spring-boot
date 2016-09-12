package io.swagger.sample.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.sample.data.PetData;
import io.swagger.sample.exception.AlreadyExistedException;
import io.swagger.sample.exception.NotFoundException;
import io.swagger.sample.models.Category;
import io.swagger.sample.models.Pet;

@RestController
public class PetResource extends AbstractResource {
	
    private static PetData data = new PetData();
    
    @ApiOperation(notes = "Returns a pet when 0 < ID <= 10.  ID > 10 or non-integers will simulate API error conditions", value = "Find pet by ID", nickname = "getPetById",
        tags = {"Pets"} )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Nice!", response = Pet.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = io.swagger.sample.models.ApiResponse.class),
        @ApiResponse(code = 404, message = "Pet not found", response = io.swagger.sample.models.ApiResponse.class)
    })
    @RequestMapping(
    		value = "/pets/{id}", 
    		method = RequestMethod.GET, 
    		produces = "application/json"
    )
    public ResponseEntity<Pet> getPetById(
    		@ApiParam(value = "ID of pet that needs to be fetched", allowableValues = "range[1,10]", required = true) @PathVariable("id") Integer petId
    ) throws Exception {
        Pet pet = data.getPetById(petId);
        if(pet != null) {
            return ResponseEntity.ok().body(pet);
        }
        else {
            throw new NotFoundException(io.swagger.sample.models.ApiResponse.ERROR, "Pet " + petId + " not found");
        }
    }
    
    @ApiOperation(notes = "Add a pet", value = "Add a pet", nickname = "addPet",
            tags = {"Pets"} )
        @ApiResponses({
            @ApiResponse(code = 200, message = "Nice!", response = Pet.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = io.swagger.sample.models.ApiResponse.class),
            @ApiResponse(code = 413, message = "Pet already exsited", response = io.swagger.sample.models.ApiResponse.class)
        })
        @RequestMapping(
        		value = "/pets/addPet/{id}/{name}/{catId}", 
        		method = RequestMethod.POST, 
        		produces = "application/json"
        )
        public ResponseEntity<Pet> addPet(
        		@ApiParam(value = "ID of pet that needs to be added", allowableValues = "range[1,infinity]", required = true) @PathVariable("id") Integer petId,
        		@ApiParam(value = "Name of pet that needs to be added", required = true) @PathVariable("name") String name,
        		@ApiParam(value = "ID of category this pet belonging to", allowableValues = "range[1,4]", required = true) @PathVariable("catId") Integer catId
        ) throws Exception {
    		Pet pet = new Pet();
    		pet.setId(petId);
    		pet.setName(name);
    		Category cat = new Category();
    		cat.setId(catId);
    		pet.setCategory(cat);
            if(data.addPet(pet)) {
            	return ResponseEntity.ok().body(pet);
            } else {
            	throw new AlreadyExistedException(io.swagger.sample.models.ApiResponse.ERROR, "Pet " + petId + " already exsited!");
            }
            
        }
    
}
