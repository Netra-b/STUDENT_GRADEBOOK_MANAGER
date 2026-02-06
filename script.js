
// Navigation Handler
function showSection(sectionId) {
    // Hide all sections
    document.querySelectorAll('.content section').forEach(section => {
        section.classList.remove('active-section');
    });

    // Show selected section
    const activeSection = document.getElementById(sectionId);
    if (activeSection) {
        activeSection.classList.add('active-section');
    }

    // Update active nav button
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    event.currentTarget.classList.add('active');
}

// Mock Database (Since backend integration is future enhancement)
let students = [
    { usn: 101, name: "Alice Smith", branch: "CSE", cgpa: 8.5, backlogs: 0 },
    { usn: 102, name: "Charlie Brown", branch: "ECE", cgpa: 9.0, backlogs: 0 },
    { usn: 105, name: "Bob Johnson", branch: "ISE", cgpa: 6.8, backlogs: 1 }
];

// Handle Insert
function handleInsert(e) {
    e.preventDefault();
    const btn = e.target.querySelector('button[type="submit"]');
    const originalText = btn.innerText;

    // Simulate loading
    btn.innerText = "Processing...";
    btn.style.opacity = "0.7";

    setTimeout(() => {
        alert("Student added successfully (Frontend Prototype)");
        e.target.reset();
        btn.innerText = originalText;
        btn.style.opacity = "1";
    }, 1000);
}

// Handle Search
function handleSearch() {
    const input = document.getElementById('searchInput').value;
    const resultCard = document.getElementById('searchResult');
    const student = students.find(s => s.usn == input);

    if (student) {
        // Populate Data
        document.getElementById('resName').innerText = student.name;
        document.getElementById('resUsn').innerText = student.usn;
        document.getElementById('resBranch').innerText = student.branch;
        document.getElementById('resCgpa').innerText = student.cgpa;

        const statusEl = document.getElementById('resStatus');
        const isEligible = student.cgpa >= 7.0 && student.backlogs == 0;

        statusEl.className = `status ${isEligible ? 'eligible' : 'not-eligible'}`;
        statusEl.innerText = isEligible ? 'Eligible' : 'Not Eligible';

        resultCard.classList.remove('hidden');
    } else {
        alert("Student not found!");
        resultCard.classList.add('hidden');
    }
}

// Handle Update
function handleUpdate(e) {
    e.preventDefault();
    alert("Student details updated successfully!");
}

// Initialize Dashboard
function initDashboard() {
    // Dynamically load table rows from mock data
    const tbody = document.getElementById('studentTableBody');
    tbody.innerHTML = '';

    students.forEach(s => {
        const isEligible = s.cgpa >= 7.0 && s.backlogs == 0;
        const row = `
            <tr>
                <td>${s.usn}</td>
                <td>${s.name}</td>
                <td>${s.branch}</td>
                <td>${s.cgpa}</td>
                <td><span class="status ${isEligible ? 'eligible' : 'not-eligible'}">
                    ${isEligible ? 'Eligible' : 'Not Eligible'}
                </span></td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

// Run on load
document.addEventListener('DOMContentLoaded', initDashboard);
