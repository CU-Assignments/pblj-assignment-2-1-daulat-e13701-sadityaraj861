import java.util.*;

class Card {
    private String symbol;
    private int number;

    public Card(String symbol, int number) {
        this.symbol = symbol;
        this.number = number;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return symbol + " " + number;
    }
}

public class CardGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<Card>> cardMap = new HashMap<>();

        System.out.println("Enter Number of Cards :");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.println("Enter card " + i + ":");
            String symbol = scanner.nextLine();
            int number = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            cardMap.putIfAbsent(symbol, new ArrayList<>());
            cardMap.get(symbol).add(new Card(symbol, number));
        }

        // Get distinct symbols and sort them
        List<String> symbols = new ArrayList<>(cardMap.keySet());
        Collections.sort(symbols);
        
        System.out.println("Distinct Symbols are :");
        for (String symbol : symbols) {
            System.out.print(symbol + " ");
        }
        System.out.println();

        // Print cards for each symbol
        for (String symbol : symbols) {
            List<Card> cards = cardMap.get(symbol);
            int sum = 0;
            
            System.out.println("Cards in " + symbol + " Symbol");
            for (Card card : cards) {
                System.out.println(card);
                sum += card.getNumber();
            }
            
            System.out.println("Number of cards : " + cards.size());
            System.out.println("Sum of Numbers : " + sum);
        }

        scanner.close();
    }
}
