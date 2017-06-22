package com.sherybedrock.numberencoding.comparators;

import org.junit.Test;

import com.sherybedrock.numberencoding.comparators.GermanWordComparator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GermanWordComparatorTest {

	@Test
	public void testCompareWords() {
		GermanWordComparator germanWordComparator = new GermanWordComparator();
		// Test equal words
		assertThat(germanWordComparator.compare("Abgd", "Abgd"), is(0));
		assertThat(germanWordComparator.compare("Abgd\"", "Abgd\""), is(0));
		assertThat(germanWordComparator.compare("226787976909860", "226787976909860"), is(0));
		assertThat(germanWordComparator.compare("\"226787976909860\"", "\"226787976909860\""), is(0));
		assertThat(germanWordComparator.compare("%#$&%*(*&#%$^%@$", "%#$&%*(*&#%$^%@$"), is(0));
		assertThat(germanWordComparator.compare("\"%#$&%*(*&#%$^%@$", "\"%#$&%*(*&#%$^%@$"), is(0));

		// Test first word greater than the second
		assertThat(germanWordComparator.compare("Abgd", "Bbgd"), is(-1));
		assertThat(germanWordComparator.compare("Abgd\"", "Bbgd\""), is(-1));
		assertThat(germanWordComparator.compare("226787976909860", "926787976909860"), is(-7));
		assertThat(germanWordComparator.compare("\"226787976909860\"", "\"926787976909860\""), is(-7));
		assertThat(germanWordComparator.compare("5%#$&%*(*&#%$^%@$", "8%#$&%*(*&#%$^%@$"), is(-3));
		assertThat(germanWordComparator.compare("\"5%#$&%*(*&#%$^%@$", "\"8%#$&%*(*&#%$^%@$"), is(-3));

		// Test second word greater than the first
		assertThat(germanWordComparator.compare("Bbgd", "Abgd"), is(1));
		assertThat(germanWordComparator.compare("Bbgd\"", "Abgd\""), is(1));
		assertThat(germanWordComparator.compare("926787976909860", "226787976909860"), is(7));
		assertThat(germanWordComparator.compare("\"926787976909860\"", "\"226787976909860\""), is(7));
		assertThat(germanWordComparator.compare("8%#$&%*(*&#%$^%@$", "5%#$&%*(*&#%$^%@$"), is(3));
		assertThat(germanWordComparator.compare("\"8%#$&%*(*&#%$^%@$", "\"5%#$&%*(*&#%$^%@$"), is(3));

	}
}
