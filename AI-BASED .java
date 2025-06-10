import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.eval.*;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

import java.io.File;
import java.util.List;

public class RecommenderSystem {

    public static void main(String[] args) throws Exception {
        // Load dataset
        DataModel model = new FileDataModel(new File("data/dataset.csv"));

        // Calculate similarity between users
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // Define user neighborhood
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);

        // Build the recommender
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Recommend items for a given user
        List<RecommendedItem> recommendations = recommender.recommend(1, 3);

        // Print recommendations
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Recommended Item ID: " + recommendation.getItemID() +
                               " with Value: " + recommendation.getValue());
        }
    }
}