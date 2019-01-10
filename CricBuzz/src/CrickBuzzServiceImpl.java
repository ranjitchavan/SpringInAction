
public class CrickBuzzServiceImpl implements CrickBuzzService{
	private ExternalICCScoreCompService extComp;
	
	
	public void setExtComp(ExternalICCScoreCompService extComp) {
		this.extComp = extComp;
	}



	@Override
	public String findScore(int matchID) {
		// TODO Auto-generated method stub
		return extComp.getScore(matchID);
	}
	
}
