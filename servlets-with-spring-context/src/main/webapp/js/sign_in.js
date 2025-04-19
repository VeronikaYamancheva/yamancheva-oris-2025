const form = document.querySelector('#logonForm');
const nicknameField = document.querySelector('#nickname');
const passwordField = document.querySelector('#password');
const submitBtn = document.querySelector('#submit-button');
const serverErrors = document.querySelector('.server-valid-errors');

submitBtn.addEventListener('click', function (ev) {
    ev.preventDefault();
    clearErrors();

    let isValid = true;

    if (nicknameField.value === '') {
        showError(nicknameField.parentNode, "Nickname shouldn't be empty");
        isValid = false;
    }
    if (nicknameField.value.length > 10) {
        showError(nicknameField.parentNode, "Nickname shouldn't be longer than 10 characters.");
        isValid = false;
    }
    if (passwordField.value === '') {
        showError(passwordField.parentNode, "Password shouldn't be empty");
        isValid = false;
    }
    if (passwordField.value.length < 8) {
        showError(passwordField.parentNode, 'Password should have at least 8 symbols.');
        isValid = false;
    }

    if (isValid) {
        form.submit();
    }
});


function showError(node, message) {
    const errorDiv = document.createElement('div');
    errorDiv.classList.add('input-error');
    errorDiv.textContent = message;
    node.appendChild(errorDiv);
}

function clearErrors() {
    const errorElements = document.querySelectorAll('.input-error');
    errorElements.forEach(error => error.remove());
    if (serverErrors) {
        serverErrors.textContent = '';
    }
}


nicknameField.addEventListener('input', () => {
    removeError(nicknameField);
    if (nicknameField.value.length > 10) {
        showError(nicknameField.parentNode, "Nickname shouldn't be longer than 10 characters.");
    }
});

passwordField.addEventListener('input', () => {
    removeError(passwordField);
    if (passwordField.value.length < 8) {
        showError(passwordField.parentNode, 'Password should have at least 8 symbols.');
    }
});

function removeError(field) {
    const errorElem = Array.from(field.parentNode.querySelectorAll('.input-error')); // Преобразование NodeList в массив
    errorElem.forEach(error => error.remove());
}

function formHasErrors(form) {
    const errors = form.querySelectorAll('.input-error');
    return errors.length > 0;
}