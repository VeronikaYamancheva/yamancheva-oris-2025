function profileEdit() {
    const textElements = document.querySelectorAll('.text');
    const inputElements = document.querySelectorAll('.input');
    const editButton = document.getElementById('editButton');
    const isEditing = editButton.textContent === 'Редактировать';

    if (isEditing) {
        textElements.forEach((el, index) => {
            el.style.display = 'none';
            inputElements[index].style.display = 'inline';
            inputElements[index].focus();
        });
        editButton.textContent = 'Сохранить';
    } else {
        textElements.forEach((el, index) => {
            el.style.display = 'inline';
            inputElements[index].style.display = 'none';
        });
        editButton.textContent = 'Редактировать';
        document.getElementById('profileForm').submit();
    }
}