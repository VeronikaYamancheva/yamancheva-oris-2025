const form = document.getElementById('loginForm');
const emailField = document.getElementById('email');
const nicknameField = document.getElementById('nickname');
const passwordField = document.getElementById('password');
const confirmPasswordField = document.getElementById('cpassword');
const submitButton = document.getElementById('submit-button');

submitButton.addEventListener('click', (ev) => {
    ev.preventDefault();
    clearErrors();

    let isValid = true;

    isValid = validateField(emailField, isEmailValid, 20, "Email") && isValid;

    isValid = validateField(nicknameField, null, 10, "Nickname") && isValid;

    isValid = validateField(passwordField, null, 1000, "Password") && isValid;

    isValid = validateConfirmPassword(confirmPasswordField, passwordField) && isValid;

    if (isValid) {
        form.submit();
    }
});

function validateField(field, validator, maxLength, fieldName) {
    removeError(field);
    if (field.value.trim() === '') {
        showError(field.parentNode, `${fieldName} shouldn't be empty`);
        return false;
    }
    if (field.value.length > maxLength && maxLength > 0) {
        removeError(field);
        showError(field.parentNode, `${fieldName} shouldn't have more than ${maxLength} characters.`);
        return false;
    }
    if (validator && !validator(field.value)) {
        removeError(field);
        showError(field.parentNode, `${fieldName} is invalid.`);
        return false;
    }
    return true;
}

function validateConfirmPassword(confirmPasswordField, passwordField) {
    removeError(confirmPasswordField);
    if (confirmPasswordField.value !== passwordField.value) {
        showError(confirmPasswordField.parentNode, "Passwords don't match.");
        return false;
    }
    return true;
}

function isEmailValid(emailValue) {
    const emailPattern = /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/iu;
    return emailPattern.test(emailValue)
}

emailField.addEventListener('input', function() {
    removeError(emailField);
    if (!isEmailValid(emailField.value)) {
        showError(emailField.parentNode, 'Email is invalid');
    }
})

nicknameField.addEventListener('input', () => {
    removeError(nicknameField);
    if (nicknameField.value.length > 10) {
        showError(nicknameField.parentNode, "Nickname shouldn't be longer than 10 characters.");
    }
});

passwordField.addEventListener('input', () => {
    removeError(passwordField);
    if (passwordField.value.length < 8) {
        showError(passwordField.parentNode, "Password shouldn't be shorter than 8 characters.");
    }
});

confirmPasswordField.addEventListener('input', () => {
    removeError(confirmPasswordField);
    if (confirmPasswordField.value.length < 8) {
        showError(confirmPasswordField.parentNode, "Password shouldn't be shorter than 8 characters.");
    } else {
        validateConfirmPassword(confirmPasswordField, passwordField);
    }
});

function showError(node, message) {
    const errorDiv = document.createElement('div');
    errorDiv.classList.add('input-error');
    errorDiv.textContent = message;
    node.appendChild(errorDiv);
}

function removeError(field) {
    const errorElements = Array.from(field.parentNode.querySelectorAll('.input-error'));
    errorElements.forEach(error => error.remove());
}

function clearErrors() {
    const errorElements = document.querySelectorAll('.input-error');
    errorElements.forEach(error => error.remove());
}