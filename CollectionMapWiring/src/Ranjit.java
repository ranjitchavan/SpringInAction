import java.util.Collection;
import java.util.List;

public class Ranjit implements Performer {
	
	private Collection<Instrument> instrument;
	public List<Instrument> getInstrument() {
		return (List<Instrument>) instrument;
	}

	public void setInstrument(List<Instrument> instrument) {
		this.instrument = instrument;
	}

	public Ranjit() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
		for(Instrument ist:instrument){
			ist.play();
		}

	}

}
