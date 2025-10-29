package seedu.address.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MultiIndex}.
 */
public class MultiIndexTest {

    @Test
    public void constructor_validSingleIndex_success() {
        Index index = Index.fromOneBased(3);
        MultiIndex multiIndex = new MultiIndex(index);

        assertEquals(index, multiIndex.getLowerBound());
        assertEquals(index, multiIndex.getUpperBound());
        assertTrue(multiIndex.isSingle());
        assertEquals("3", multiIndex.toString());
    }

    @Test
    public void constructor_validRange_success() {
        Index lower = Index.fromOneBased(2);
        Index upper = Index.fromOneBased(5);
        MultiIndex range = new MultiIndex(lower, upper);

        assertEquals(lower, range.getLowerBound());
        assertEquals(upper, range.getUpperBound());
        assertFalse(range.isSingle());
        assertEquals("2:5", range.toString());
    }

    @Test
    public void constructor_invalidRange_throwsIllegalArgumentException() {
        Index lower = Index.fromOneBased(5);
        Index upper = Index.fromOneBased(3);

        assertThrows(IllegalArgumentException.class, () -> new MultiIndex(lower, upper));
    }

    @Test
    public void toIndexList_singleIndex_returnsSingleElementList() {
        MultiIndex single = new MultiIndex(Index.fromOneBased(4));
        List<Index> list = single.toIndexList();

        assertEquals(1, list.size());
        assertEquals(Index.fromOneBased(4), list.get(0));
    }

    @Test
    public void toIndexList_validRange_returnsInclusiveList() {
        MultiIndex range = new MultiIndex(Index.fromOneBased(1), Index.fromOneBased(3));
        List<Index> list = range.toIndexList();

        assertEquals(List.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3)), list);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        MultiIndex range = new MultiIndex(Index.fromOneBased(1), Index.fromOneBased(3));
        assertEquals(range, range);
    }

    @Test
    public void equals_equalObjects_returnsTrue() {
        MultiIndex a = new MultiIndex(Index.fromOneBased(2), Index.fromOneBased(4));
        MultiIndex b = new MultiIndex(Index.fromOneBased(2), Index.fromOneBased(4));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals_differentBounds_returnsFalse() {
        MultiIndex a = new MultiIndex(Index.fromOneBased(1), Index.fromOneBased(3));
        MultiIndex b = new MultiIndex(Index.fromOneBased(2), Index.fromOneBased(3));

        assertNotEquals(a, b);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        MultiIndex a = new MultiIndex(Index.fromOneBased(1), Index.fromOneBased(3));
        assertNotEquals(a, "Not a MultiIndex");
    }

    @Test
    public void hashCode_equalObjects_sameHash() {
        MultiIndex a = new MultiIndex(Index.fromOneBased(1), Index.fromOneBased(3));
        MultiIndex b = new MultiIndex(Index.fromOneBased(1), Index.fromOneBased(3));

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toString_singleIndex_correctFormat() {
        MultiIndex single = new MultiIndex(Index.fromOneBased(7));
        assertEquals("7", single.toString());
    }

    @Test
    public void toString_range_correctFormat() {
        MultiIndex range = new MultiIndex(Index.fromOneBased(2), Index.fromOneBased(6));
        assertEquals("2:6", range.toString());
    }
}
