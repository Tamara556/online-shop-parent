// Set current year in footer
document.getElementById('current-year').textContent = new Date().getFullYear();

// Mobile menu functionality
const mobileMenuBtn = document.querySelector('.mobile-menu-btn');
const navLinks = document.querySelector('.nav-links');

if (mobileMenuBtn) {
    mobileMenuBtn.addEventListener('click', () => {
        navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
    });
}

// Handle window resize
window.addEventListener('resize', () => {
    if (window.innerWidth >= 768) {
        if (navLinks) {
            navLinks.style.display = 'flex';
        }
    } else {
        if (navLinks) {
            navLinks.style.display = 'none';
        }
    }
});

// Initialize any page-specific functionality
function initializePage() {
    const currentPage = window.location.pathname.split('/').pop();

    // Home page
    if (currentPage === '' || currentPage === 'index.html') {
        console.log('Home page initialized');
    }
    // Shop page
    else if (currentPage === 'shop.html') {
        console.log('Shop page initialized');
        initializeShopPage();
    }
    // Contact page
    else if (currentPage === 'contact.html') {
        console.log('Contact page initialized');
        initializeContactForm();
    }
    // Login page
    else if (currentPage === 'login.html') {
        console.log('Login page initialized');
        initializeLoginForm();
    }
    // Register page
    else if (currentPage === 'register.html') {
        console.log('Register page initialized');
        initializeRegisterForm();
    }
}

// Shop page functionality
function initializeShopPage() {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    if (tabButtons.length && tabContents.length) {
        tabButtons.forEach(button => {
            button.addEventListener('click', () => {
                // Remove active class from all buttons and contents
                tabButtons.forEach(btn => btn.classList.remove('active'));
                tabContents.forEach(content => content.classList.remove('active'));

                // Add active class to current button and content
                button.classList.add('active');
                const category = button.dataset.category;
                document.querySelector(`.tab-content[data-category="${category}"]`).classList.add('active');
            });
        });
    }
}

// Contact form functionality
function initializeContactForm() {
    const contactForm = document.getElementById('contact-form');

    if (contactForm) {
        contactForm.addEventListener('submit', (e) => {
            e.preventDefault();

            // Validate form
            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const subject = document.getElementById('subject').value;
            const message = document.getElementById('message').value;

            let isValid = true;

            if (name.length < 2) {
                showError('name', 'Name must be at least 2 characters');
                isValid = false;
            } else {
                clearError('name');
            }

            if (!validateEmail(email)) {
                showError('email', 'Please enter a valid email address');
                isValid = false;
            } else {
                clearError('email');
            }

            if (subject.length < 5) {
                showError('subject', 'Subject must be at least 5 characters');
                isValid = false;
            } else {
                clearError('subject');
            }

            if (message.length < 10) {
                showError('message', 'Message must be at least 10 characters');
                isValid = false;
            } else {
                clearError('message');
            }

            if (isValid) {
                // Submit form logic would go here
                alert('Form submitted successfully!');
                contactForm.reset();
            }
        });
    }
}

// Login form functionality
function initializeLoginForm() {
    const loginForm = document.getElementById('login-form');

    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            let isValid = true;

            if (!validateEmail(email)) {
                showError('email', 'Please enter a valid email address');
                isValid = false;
            } else {
                clearError('email');
            }

            if (password.length < 1) {
                showError('password', 'Password is required');
                isValid = false;
            } else {
                clearError('password');
            }

            if (isValid) {
                // Login logic would go here
                alert('Login successful!');
            }
        });
    }
}

// Register form functionality
function initializeRegisterForm() {
    const registerForm = document.getElementById('register-form');

    if (registerForm) {
        registerForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm-password').value;
            const terms = document.getElementById('terms').checked;

            let isValid = true;

            if (name.length < 2) {
                showError('name', 'Name must be at least 2 characters');
                isValid = false;
            } else {
                clearError('name');
            }

            if (!validateEmail(email)) {
                showError('email', 'Please enter a valid email address');
                isValid = false;
            } else {
                clearError('email');
            }

            if (password.length < 6) {
                showError('password', 'Password must be at least 6 characters');
                isValid = false;
            } else {
                clearError('password');
            }

            if (password !== confirmPassword) {
                showError('confirm-password', 'Passwords do not match');
                isValid = false;
            } else {
                clearError('confirm-password');
            }

            if (!terms) {
                showError('terms', 'You must agree to the terms and conditions');
                isValid = false;
            } else {
                clearError('terms');
            }

            if (isValid) {
                // Register logic would go here
                alert('Registration successful!');
                registerForm.reset();
            }
        });
    }
}

// Helper functions
function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function showError(id, message) {
    const element = document.getElementById(id);
    const errorElement = document.getElementById(`${id}-error`) || createErrorElement(id);

    element.classList.add('error');
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

function clearError(id) {
    const element = document.getElementById(id);
    const errorElement = document.getElementById(`${id}-error`);

    if (element) {
        element.classList.remove('error');
    }

    if (errorElement) {
        errorElement.style.display = 'none';
    }
}

function createErrorElement(id) {
    const element = document.getElementById(id);
    const errorElement = document.createElement('div');

    errorElement.id = `${id}-error`;
    errorElement.className = 'error-message';

    if (element.parentNode) {
        element.parentNode.insertBefore(errorElement, element.nextSibling);
    }

    return errorElement;
}

// Initialize the page
document.addEventListener('DOMContentLoaded', initializePage);