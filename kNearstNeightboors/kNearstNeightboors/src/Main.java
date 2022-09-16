import java.util.*;

public class Main {

    private static Random random = new Random();

    private enum CategoryEnum {
        Fantasy,
        Action,
        Romantic
    };

    private static Map<Main.CategoryEnum, Category> categories = new HashMap<>();
    private static List<Film> films = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    private static History history = new History();
    public static void main(String[] args) {
        Main.categories = Main.initCategories();
        Main.films = Main.initFilms();
        Main.users = Main.initUsers();

        // Print all films with his rate
        for (Film film : Main.films) {
            System.out.printf("Film: %s; Category: %s; Rate: %f;\n",
                    film.getName(),
                    film.getCategory().getName(),
                    film.getRate());
        }

        System.out.println(Main.history);

        System.out.println(Main.recommendedFilm(Main.users.get(0)));
    }

    public static Map<Main.CategoryEnum, Category> initCategories() {
        return new HashMap<Main.CategoryEnum, Category>(Map.ofEntries(
                Map.entry(CategoryEnum.Fantasy, new Category("Fantasy")),
                Map.entry(CategoryEnum.Action, new Category("Action")),
                Map.entry(CategoryEnum.Romantic, new Category("Romantic"))
        ));
    }

    public static List<Film> initFilms() {
        return new ArrayList<>(List.of(
                new Film("Spider-man", Main.categories.get(CategoryEnum.Fantasy)),
                new Film("Carrier", Main.categories.get(CategoryEnum.Action)),
                new Film("Just Go With It", Main.categories.get(CategoryEnum.Romantic)),
                new Film("Iron man", Main.categories.get(CategoryEnum.Fantasy)),
                new Film("Die Hard", Main.categories.get(CategoryEnum.Action))
        ));
    }

    public static List<User> initUsers() {
        Category fantasy = Main.categories.get(CategoryEnum.Fantasy);
        Category action = Main.categories.get(CategoryEnum.Action);
        Category romantic = Main.categories.get(CategoryEnum.Romantic);

        User artem = new User("Artem", new HashMap<Category, Integer>(Map.ofEntries(
                Map.entry(fantasy, 10),
                Map.entry(action, 7),
                Map.entry(romantic, 10)
        )));

        User oleg = new User("Oleg", new HashMap<Category, Integer>(Map.ofEntries(
                Map.entry(fantasy, 5),
                Map.entry(action, 10),
                Map.entry(romantic, 0)
        )));

        User anna = new User("Anna", new HashMap<Category, Integer>(Map.ofEntries(
                Map.entry(fantasy, 5),
                Map.entry(action, 0),
                Map.entry(romantic, 10)
        )));

        User olesya = new User("Olesya", new HashMap<Category, Integer>(Map.ofEntries(
                Map.entry(fantasy, 7),
                Map.entry(action, 5),
                Map.entry(romantic, 7)
        )));

        User katya = new User("Katya", new HashMap<Category, Integer>(Map.ofEntries(
                Map.entry(fantasy, 3),
                Map.entry(action, 3),
                Map.entry(romantic, 3)
        )));

        List<User> users = new ArrayList<>(List.of(artem, oleg, anna, olesya, katya));

        // Watch films
        for (User user : users) {
            for (Film film : Main.films) {
                if (Math.random() < 0.7) {
                    user.watchFilm(film, Math.random() * 5, Main.history);
                }
            }
        }

        return users;
    }

    private static RecommendedFilm recommendedFilm(User user) {
        List<User> threeNearestFriend = Main.findToUserNearestNFriends(user, 3);

        System.out.println("Nearest users:");

        System.out.print(user);

        if (threeNearestFriend == null) {
            System.out.println("Not found nearest users");
            return null;
        }

        for (User nearestUser : threeNearestFriend) {
            System.out.print(nearestUser);
        }

        Map<Film, List<Double>> allViewFilmsByFriendsAndTheyRates = new HashMap<>();

        for (User nearestUser : threeNearestFriend) {
            Map<Film, Double> viewsUserFilms = Main.history.get(nearestUser);
            for (Film film : viewsUserFilms.keySet()) {
                if (!Main.history.get(user).containsKey(film)) {
                    if (allViewFilmsByFriendsAndTheyRates.containsKey(film)) {
                        allViewFilmsByFriendsAndTheyRates.get(film).add(viewsUserFilms.get(film));
                    } else {
                        allViewFilmsByFriendsAndTheyRates.put(film, new ArrayList<>(List.of(viewsUserFilms.get(film))));
                    }
                }
            }
        }

        Film recommendedFilm = null;
        double maxRate = 0;
        for (var filmViews : allViewFilmsByFriendsAndTheyRates.entrySet()) {
            double summaryRate = 0;
            for (double rate : filmViews.getValue()) {
                summaryRate += rate;
            }
            summaryRate /= filmViews.getValue().size();
            if (summaryRate > maxRate) {
                recommendedFilm = filmViews.getKey();
                maxRate = summaryRate;
            }
        }

        return new RecommendedFilm(recommendedFilm, maxRate);
    }

    private static List<User> findToUserNearestNFriends(User currentUser, int N) {
        if (Main.users.size() <= N) return null;

        Map<Category, Integer> evaluateCategoryCurrentUser = currentUser.getEvaluateCategory();
        Map<User, Double> nearestUsers = new HashMap<>();

        for (User user : Main.users) {
            if (currentUser != user) {
                double sumSquareCategoryEvaluate = 0;
                Map<Category, Integer> evaluateCategoryUser = user.getEvaluateCategory();
                for (Category category : evaluateCategoryUser.keySet()) {
                    Integer evaluateCurrentUser = evaluateCategoryCurrentUser.get(category);
                    Integer evaluateUser = evaluateCategoryUser.get(category);

                    sumSquareCategoryEvaluate += (evaluateCurrentUser - evaluateUser) ^ 2;
                }
                sumSquareCategoryEvaluate = Math.sqrt(sumSquareCategoryEvaluate);
                nearestUsers.put(user, sumSquareCategoryEvaluate);
            }
        }

        List<Map.Entry<User, Double>> nearestUsersList =
                Main.qSort(nearestUsers.entrySet().stream().toList());

        List<User> foundNearestUsers = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            foundNearestUsers.add(nearestUsersList.get(i).getKey());
        }

        return foundNearestUsers;
    }

    private static List<Map.Entry<User, Double>> qSort(List<Map.Entry<User, Double>> nearestUsers) {
        if (nearestUsers.size() == 0 || nearestUsers.size() == 1) {
            return nearestUsers;
        }

        List<Map.Entry<User, Double>> baseList = new ArrayList<>();
        List<Map.Entry<User, Double>> leftList = new ArrayList<>();
        List<Map.Entry<User, Double>> rightList = new ArrayList<>();

        int randomIndex = Main.random.nextInt(nearestUsers.size());
        Map.Entry<User, Double> randomUser = nearestUsers.get(randomIndex);

        for (int i = 0; i < nearestUsers.size(); i++) {
            if (i == randomIndex) continue;

            if (nearestUsers.get(i).getValue() < randomUser.getValue()) {
                leftList.add(nearestUsers.get(i));
            } else {
                rightList.add(nearestUsers.get(i));
            }
        }

        baseList.addAll(Main.qSort(leftList));
        baseList.add(randomUser);
        baseList.addAll(Main.qSort(rightList));

        return baseList;
    }
}