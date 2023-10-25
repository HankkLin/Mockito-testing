package sde.virginia.edu.hw4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    private CatalogService catalogService;
    @Mock
    Section section;

    @Test
    void random(){
        catalogService.add(section);
    }

}
