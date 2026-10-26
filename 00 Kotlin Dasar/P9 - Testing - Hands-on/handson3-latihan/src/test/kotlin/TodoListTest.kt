import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

// Hands-on 3: Test Lifecycle & Setup dengan @BeforeEach
// Tugas: Lengkapi setUp() supaya setiap test mendapat TodoList yang BARU dan
// KOSONG (tidak saling memengaruhi satu sama lain), lalu lengkapi ketiga test.
//
// CATATAN: file ini SENGAJA membuat semua test gagal (merah) sampai kamu
// melengkapi seluruh TODO -- itu normal untuk latihan ini!

class TodoListTest {

    private lateinit var todoList: TodoList

    @BeforeEach
    fun setUp() {
        // Inisialisasi todoList dengan TodoList() yang baru sebelum setiap test
        todoList = TodoList()
    }

    @Test
    @DisplayName("TodoList baru harus kosong")
    fun testNewListIsEmpty() {
        assertTrue(todoList.isEmpty())
    }

    @Test
    @DisplayName("Menambah item menaikkan ukuran list")
    fun testAddIncreasesSize() {
        todoList.add("Belajar Kotlin")
        assertEquals(1, todoList.size())
    }

    @Test
    @DisplayName("Menghapus item mengembalikan true dan mengurangi ukuran")
    fun testRemoveExistingItem() {
        // Arrange
        todoList.add("Belajar Kotlin")

        // Act
        val removed = todoList.remove("Belajar Kotlin")

        // Assert
        assertTrue(removed)
        assertEquals(0, todoList.size())
    }
}
