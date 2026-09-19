package keystroke_auth.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BiometricService {

    private static final double SIMILARITY_THRESHOLD = 200.0;

    public double calculateEuclideanDistance(List<Double> baseline, List<Double> attempt) {
        if (baseline == null || attempt == null || baseline.size() != attempt.size()) {
            return Double.MAX_VALUE;
        }

        double sumOfSquares = 0.0;
        for (int i = 0; i < baseline.size(); i++) {
            double delta = baseline.get(i) - attempt.get(i);
            sumOfSquares += (delta * delta);
        }

        return Math.sqrt(sumOfSquares);
    }

    public boolean isGenuineTypist(List<Double> baseline, List<Double> attempt) {
        double distance = calculateEuclideanDistance(baseline, attempt);
        return distance <= SIMILARITY_THRESHOLD;
    }
}