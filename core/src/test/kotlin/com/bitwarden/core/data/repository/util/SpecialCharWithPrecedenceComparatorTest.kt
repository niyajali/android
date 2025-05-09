package com.bitwarden.core.data.repository.util

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class SpecialCharWithPrecedenceComparatorTest {

    @Test
    fun `Sorting with comparator should return expected result of sorted string`() {
        val unsortedList = listOf(
            "__Za",
            "z",
            "___",
            "1a3",
            "aBc",
            "__a",
            "__A",
            "__a",
            "__4",
            "Z",
            "__3",
            "Abc",
        )
        val expectedSortedList = listOf(
            "___",
            "__3",
            "__4",
            "__a",
            "__a",
            "__A",
            "__Za",
            "1a3",
            "aBc",
            "Abc",
            "z",
            "Z",
        )

        assertEquals(
            expectedSortedList,
            unsortedList.sortedWith(SpecialCharWithPrecedenceComparator),
        )
    }
}
