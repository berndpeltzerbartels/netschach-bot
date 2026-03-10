package de.netschach.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    /*
    @Test
    void gameStatus() throws Exception {
        List<String> moves = Arrays.asList("e2e4");
        String json = new ObjectMapper().writeValueAsString(moves);
        GameStatusTO gameStatus = new ObjectMapper().readValue(this.mockMvc.perform(MockMvcRequestBuilders //
                .post("/api/chess/v1/gamestatus") //
                .header("Content-type", "application/json") //
                .content(json)) //
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(), GameStatusTO.class);
        Assert.assertEquals(gameStatus.pieces().get("e4").toString(), "P");
    }
     */
}
