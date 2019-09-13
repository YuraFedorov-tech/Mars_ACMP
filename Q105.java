import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Q105 {

	public static void main(String[] args) {
		Scanner M = new Scanner(System.in);
		String s = M.nextLine().trim();
		Go(s);

	}

	private static void Go(String s) {
		String s1 = FindS1(s);
		String s2 = FindS2(s, s1.length());
		s1 = DeleteSpace(s1);
		int beginSkobka = FindBeginSkoba(s1);
		s1 = s1.substring(beginSkobka, s1.length() - beginSkobka);
		long answear = FindNNN(s1);
		ArrayList<Integer> znak012 = new ArrayList<Integer>();
		ArrayList<Integer> number = new ArrayList<Integer>();
		ArrayList<Integer> bracket = new ArrayList<Integer>();
		ArrayList<Long> bracketMean = new ArrayList<Long>();
		ArrayList<Boolean> bracketBoolean = new ArrayList<Boolean>();
		DO_List(znak012, number, bracket, s2);
		int secondBracket = MinusLessBreket(number, bracket);
		int[] z = new int[number.size()];
		Arrays.fill(z, -1);
		DO_bracketBoolean(bracketBoolean, bracket);
		DO_bracketMean(bracketMean, bracket);
		boolean[] ansFind = { false };
		PrepearToREK(0, ansFind, number, bracket, znak012, 0, bracketMean,
				bracketBoolean, answear, z, 0);
		if (ansFind[0]) {
			ArrayList<Integer> lis = new ArrayList<Integer>();
			for (int i = 0; i < z.length; i++)
				if (z[i] != -1)
					lis.add(z[i]);
			pr(lis, s1, s2, number, bracket, beginSkobka, secondBracket,
					answear);

		} else
			System.out.print(-1);

	}

	private static long FindNNN(String s) {
		String s1 = "";

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (NumberOrMinus(ch))
				s1 += ch;

		}
		if (s1.charAt(0) == '0' & s1.length() != 1)
			s1 = s1.substring(1, s1.length());
		long in = Long.parseLong(s1);

		return in;
	}

	private static void pr(ArrayList<Integer> lis, String s1, String s2,
			ArrayList<Integer> number, ArrayList<Integer> bracket,
			int beginSkobka, int secondBracket, long answear) {
		prinANS(beginSkobka, answear, s1);
		prinRight(s2, number, bracket, lis, secondBracket);

	}

	private static void prinRight(String s2, ArrayList<Integer> number,
			ArrayList<Integer> bracket, ArrayList<Integer> lis,
			int secondBracket) {
		String[] e = { "+", "-", "*" };
		int ind = 0;
		int next = 0;
		String q = "";
		for (int i = 0; i < number.size(); i++) {
			int n = number.get(i);
			int b = bracket.get(i);
			if (b == 0) {
				String numb = Integer.toString(n);
				if (next == 1) {
					q += e[lis.get(ind)];

					ind++;
				}
				if (n < 0)
					numb = "0" + numb;
				q += numb;
				next = 1;
			}
			if (b < 100 & b > 0) {
				String numb = "(";
				if (next == 1) {
					q += e[lis.get(ind)];
					ind++;
				}
				q += numb;
				next = 0;

			}
			if (b > 100) {
				String numb = ")";

				q += numb;
				next = 1;

			}

		}
		q = FindQ(q);
		q = PlusBracket(secondBracket, q);
		System.out.print(q);

	}

	private static String FindQ(String q) {
		String s = "";
		for (int i = 1; i < q.length() - 1; i++)
			s += q.charAt(i);
		return s;
	}

	private static void prinANS(int beginSkobka, long answear, String s1) {
		String q = Long.toString(answear);
		if (answear < 0)
			q = "0" + q;
		q = PlusBracket(beginSkobka, q);
		System.out.print(q + "=");

	}

	private static String PlusBracket(int beginSkobka, String q) {
		for (int i = 0; i < beginSkobka; i++)
			q = "(" + q + ")";
		return q;
	}

	public static String FindS1(String s) {
		String s1 = "";
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '=')
				s1 = s.substring(0, i);

		return s1;
	}

	public static String DeleteSpace(String s) {
		String s1 = "";
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) != ' ')
				s1 += s.charAt(i);

		return s1;
	}

	public static int FindBeginSkoba(String s) {
		int q = 0;
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '(')
				q++;
		return q;
	}

	public static int FindN(String s) {
		String s1 = "";

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (NumberOrMinus(ch))
				s1 += ch;

		}

		int in = FindNumber(s1);
		return in;
	}

	private static int FindNumber(String s1) {
		if (s1.charAt(0) == '0' & s1.length() != 1)
			s1 = s1.substring(1, s1.length());
		int q = Integer.parseInt(s1);
		return q;

	}

	private static boolean NumberOrMinus(char ch) {
		if (ch >= '0' & ch <= '9')
			return true;
		if (ch == '-')
			return true;

		return false;
	}

	public static String FindS2(String s, int length) {
		String s2 = s.substring(length + 1, s.length());
		return s2;
	}

	public static void DO_List(ArrayList<Integer> znak012,
			ArrayList<Integer> number, ArrayList<Integer> bracket, String s2) {

		String s = "";
		int curBracket = 0;
		int maxBracket = 0;
		boolean[] e = new boolean[200];
		s2 += " ";
		for (int i = 0; i < s2.length(); i++) {
			char ch = s2.charAt(i);
			if (NumberOrMinus(ch)) {
				s += ch;
				continue;
			}
			if (ch == ' ') {
				if (!s.isEmpty())
					s = DO_N(s, number, bracket);

			}
			if (ch == '(') {
				if (!s.isEmpty())
					s = DO_N(s, number, bracket);
				number.add(1);
				maxBracket++;
				bracket.add(maxBracket);
				s = "";
			}
			if (ch == ')') {
				if (!s.isEmpty())
					s = DO_N(s, number, bracket);
				number.add(1);
				curBracket = Find_curBracket(maxBracket, e);
				bracket.add(curBracket + 100);
				s = "";
			}

		}

	}

	private static int Find_curBracket(int maxBracket, boolean[] e) {
		int i = maxBracket;
		for (; i > 0; i--)
			if (!e[i])
				break;
		e[i] = true;

		return i;
	}

	private static String DO_N(String s, ArrayList<Integer> number,
			ArrayList<Integer> bracket) {
		int n = FindNumber(s);
		number.add(n);
		bracket.add(0);
		s = "";
		return s;
	}

	public static int MinusLessBreket(ArrayList<Integer> number,
			ArrayList<Integer> bracket) {
		int ans = 0;
		while (bracket.get(0) + 100 == bracket.get(bracket.size() - 1)) {

			ans++;
			DeletLastANDFirstIndex(bracket);
			DeletLastANDFirstIndex(number);

		}
		number.add(0, 1);
		number.add(1);
		bracket.add(0, 99);
		bracket.add(199);
		return ans;
	}

	private static void DeletLastANDFirstIndex(ArrayList<Integer> bracket) {
		bracket.remove(0);
		bracket.remove(bracket.size() - 1);

	}

	public static void DO_bracketBoolean(ArrayList<Boolean> bracketBoolean,
			ArrayList<Integer> bracket) {
		for (@SuppressWarnings("unused")
		int i : bracket)
			bracketBoolean.add(false);

	}

	public static void DO_bracketMean(ArrayList<Long> bracketMean,
			ArrayList<Integer> bracket) {
		for (int i = 0; i < bracket.size(); i++)
			bracketMean.add((long) 0);

	}

	public static void PrepearToREK(int ind, boolean[] ansFind,
			ArrayList<Integer> number, ArrayList<Integer> bracket,
			ArrayList<Integer> znak012, int cur, ArrayList<Long> bracketMean,
			ArrayList<Boolean> bracketBoolean, long answear, int[] z, int m) {
		if (ansFind[0])
			return;
		if (bracketBoolean.get(0)) {
			if (bracketMean.get(0) == answear)
				ansFind[0] = true;
			return;
		}

		int[] index = FindFirstLast(bracket, bracketBoolean);
		Rekur(index[0] + 1, ansFind, number, bracket, znak012, 0, bracketMean,
				index, bracketBoolean, answear, z, m);

	}

	private static void Rekur(int step, boolean[] ansFind,
			ArrayList<Integer> number, ArrayList<Integer> bracket,
			ArrayList<Integer> znak012, long cur, ArrayList<Long> bracketMean,
			int[] index, ArrayList<Boolean> bracketBoolean, long answear,
			int[] z, int m) {
		if (step == index[1]) {
			ArrayList<Integer> bracketMeanNew = new ArrayList<Integer>();
			Long q = bracketMean.get(index[0]);
			bracketMean.set(index[0], (long) cur);
			ArrayList<Boolean> bracketBooleanNew = new ArrayList<Boolean>();
			DO_bracketBooleanNew(index, bracketBooleanNew, bracketBoolean);
			PrepearToREK(0, ansFind, number, bracket, znak012, 0, bracketMean,
					bracketBooleanNew, answear, z, m);
			bracketMean.set(index[0], q);
			return;

		}

		for (int i = 0; i < 3; i++) {
			int next = 1;
			long curNew = number.get(step);
			if (curNew == 1 & bracket.get(step) != 0) {
				curNew = bracketMean.get(step);
				next = findNext(bracket, step);
			}
			if (step == index[0] + 1) {
				Rekur(step + next, ansFind, number, bracket, znak012, curNew,
						bracketMean, index, bracketBoolean, answear, z, m);
				i = 5;
			} else {
				z[step] = i;
				znak012.add(i);
				if (i == 0)
					Rekur(step + next, ansFind, number, bracket, znak012,
							curNew + cur, bracketMean, index, bracketBoolean,
							answear, z, m + 1);
				if (i == 1)
					Rekur(step + next, ansFind, number, bracket, znak012, cur
							- curNew, bracketMean, index, bracketBoolean,
							answear, z, m + 1);
				if (i == 2)
					Rekur(step + next, ansFind, number, bracket, znak012,
							curNew * cur, bracketMean, index, bracketBoolean,
							answear, z, m + 1);
				if (ansFind[0])
					return;
				znak012.remove(znak012.size() - 1);
			}
			if (ansFind[0])
				return;
		}
		return;
	}

	private static void DO_bracketBooleanNew(int[] index,
			ArrayList<Boolean> bracketBooleanNew,
			ArrayList<Boolean> bracketBoolean) {
		for (int i = 0; i < bracketBoolean.size(); i++) {
			boolean q = bracketBoolean.get(i);
			if (i >= index[0] & i <= index[1])
				q = true;
			bracketBooleanNew.add(q);
		}

	}

	private static int findNext(ArrayList<Integer> bracket, int step) {
		int q = bracket.get(step) + 100;
		int w = bracket.indexOf(q);
		int an = w + 1 - step;

		return an;
	}

	private static int[] FindFirstLast(ArrayList<Integer> bracket,
			ArrayList<Boolean> bracketBoolean) {
		int ind[] = { 0, 0 };
		int i = 0;
		for (; i < bracket.size(); i++)
			if (bracket.get(i) >= 100 & !bracketBoolean.get(i))
				break;
		ind[1] = i;
		int q = bracket.get(i) - 100;
		ind[0] = bracket.indexOf(q);
		return ind;
	}

}