package de.netschach.stockfish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockfishDispatcherTest {

    @InjectMocks
    private StockfishDispatcher dispatcher;

    @Mock
    private StockfishRunner runner1;

    @Mock
    private StockfishRunner runner2;

    @Mock
    private StockfishRunner runner3;

    @BeforeEach
    void init() {
        when(runner1.getWaitingTime()).thenReturn(2);
        when(runner2.getWaitingTime()).thenReturn(1);
        when(runner3.getWaitingTime()).thenReturn(1);
    }


    @Test
    void dispatch() {
        StockfishTask task = StockfishTask.builder().gameId("1").level(new Level(20)).timeLimit(2).build();
        dispatcher.dispatch(task);

        verify(this.runner2).addToQueue(eq(task));
    }
}
