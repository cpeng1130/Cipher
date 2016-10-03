package Project1.Cipher;

public class FindFinalResult {
	// frex is used to recode frequency for every letter

	GetKey gk = new GetKey();
	double[] frex = new double[26];

	public void getRightResult(String plainTest, int index) {
		for (int i = 0; i < 26; i++) {
			frex[i] = 0;
		}
		int length = plainTest.length();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < 26; j++) {
				if ((plainTest.charAt(i) + "").equals((char) (j + 65) + "")) {
					frex[j]++;
				}
			}

		}
		double minTempValue = 0;
		for (int i = 0; i < 26; i++) {
			frex[i] = frex[i] / length * 100;
			minTempValue = Math.abs(gk.cdmap.get((char) (i + 65)) - frex[i])
					+ minTempValue;
		}

		System.out.println("KEY[" + index + "]" + minTempValue);
	}
		
}
