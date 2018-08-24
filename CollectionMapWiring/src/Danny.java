import java.util.Collection;
import java.util.List;

public class Danny implements Performer {
	private Instrument[] in;
	public Instrument[] getIn() {
		return in;
	}

	public void setIn(Instrument[] in) {
		this.in = in;
	}

	private List<Instrument> instrument;
	public Danny() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
		for(Instrument ist:instrument){
			ist.play();
		}
		
		for(Instrument ist:in){
			ist.play();
		}

	}
	
	public List<Instrument> getInstrument() {
		return instrument;
	}

	public void setInstrument(List<Instrument> instrument) {
		this.instrument = instrument;
	}
	
	
	 

}
