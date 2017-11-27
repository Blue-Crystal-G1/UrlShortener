package urlshortener.bluecrystal.repository.persistenceConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.net.URI;
import java.net.URISyntaxException;

@Converter(autoApply = true)
public class URIAttributeConverter implements AttributeConverter<URI, String> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database column
     */
    @Override
    public String convertToDatabaseColumn(URI attribute) {
        return (attribute == null ? null : attribute.toString());
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct dbData type for the corresponding column
     * for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param dbData the data from the database column to be converted
     * @return the converted value to be stored in the entity attribute
     */
    @Override
    public URI convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null ? null : new URI(dbData));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}