package io.nology.library.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.nology.library.book.dtos.BookResponseDTO;
import io.nology.library.book.entities.Book;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        mapper.typeMap(Book.class, BookResponseDTO.class)
                .addMappings(m -> m.map(src -> src.getGenre().getName(), BookResponseDTO::setGenre));
        return mapper;
    }

    private class StringTrimConverter implements Converter<String, String> {

        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().trim();
        }
    }

}
