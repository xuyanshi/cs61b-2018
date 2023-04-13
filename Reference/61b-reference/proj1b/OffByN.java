public class OffByN implements CharacterComparator {
    private int offBy;

    public OffByN(int N) {
        offBy = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return diff == -offBy || diff == offBy;
    }
}
