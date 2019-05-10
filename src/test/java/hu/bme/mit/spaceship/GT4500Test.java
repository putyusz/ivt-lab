package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore tsp;
  private TorpedoStore tss;

  @BeforeEach
  public void init(){
    tsp = mock(TorpedoStore.class);
    tss = mock(TorpedoStore.class);
    this.ship = new GT4500(tsp, tss);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(tsp.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(tsp, times(1)).fire(1);
    verify(tss, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(tsp.fire(1)).thenReturn(true);
    when(tss.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(tsp, times(1)).fire(1);
    verify(tss, times(1)).fire(1);

  }

  @Test
  public void primaryTorpedoCantFire(){
    when(tsp.fire(1)).thenReturn(true);
    when(tss.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(tsp, times(1)).fire(1);
    verify(tss, times(1)).fire(1);
  }

  @Test
  public void primaryTorpedoStoreEmpty(){
    when(tsp.isEmpty()).thenReturn(true);
    when(tss.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(tss,times(1)).fire(1);
  }

  @Test
  public void faultyStore(){
    when(tsp.fire(1)).thenReturn(false);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(tsp, times(1)).fire(1);
    verify(tss, times(0)).fire(1);
  }

  @Test
  public void secondaryEmpty() {
    when(tss.isEmpty()).thenReturn(true);
    when(tsp.isEmpty()).thenReturn(false);
    when(tsp.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(tsp, times(2)).fire(1);

  }

  @Test
  public void bothEmpty(){
    when(tsp.isEmpty()).thenReturn(true);
    when(tss.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(tsp, times(0)).fire(1);
    verify(tss, times(0)).fire(1);
  }

}
