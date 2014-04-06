package de.galan.verjson.jackson.p2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.galan.commons.logging.Logr;


/** x */
public class SerializeExample1 {

	final static Logger LOG = Logr.get();

	private static String outputFile = "zoo.json";


	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// let start creating the zoo
		Zoo zoo = new Zoo("Samba Wild Park", "Paz");
		Lion lion = new Lion("Simba");
		Elephant elephant = new Elephant("Manny");
		List<Animal> animals = new ArrayList<>();
		animals.add(lion);
		animals.add(elephant);
		zoo.setAnimals(animals);

		ObjectMapper mapper = new ObjectMapper();
		String zooString = mapper.writeValueAsString(zoo);
		//mapper.writerWithDefaultPrettyPrinter().writeValue(System/**/./**/out, zoo);// writeValue(new FileWriter(new File(outputFile)), zoo);
		LOG.info(zooString);

		JsonNode node = mapper.readTree(zooString);
		Zoo zoo2 = mapper.treeToValue(node, Zoo.class);
		LOG.info("" + zoo2);
	}

}
