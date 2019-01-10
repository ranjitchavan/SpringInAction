

public class ExternalICCScoreCompServiceImpl implements ExternalICCScoreCompService {

	public String getScore(int matchId) {
		if(matchId==3303)
			return "Match Score Is 230/2";
		else
			return "Not Found";
	}	

}
