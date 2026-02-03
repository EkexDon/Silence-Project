import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const entryApi = {
    getAll: () => apiClient.get('/entries'),
    create: (content: string) => apiClient.post('/entries', { content }),
    getTodayStatus: () => apiClient.get('/entries/today'),
};

export const onboardingApi = {
    getQuestions: () => apiClient.get('/onboarding/questions'),
    submit: (answers: Record<string, string>) => apiClient.post('/onboarding/submit', answers),
    getStatus: () => apiClient.get('/onboarding/status'),
};

export const intelligenceApi = {
    getSummary: () => apiClient.get('/intelligence/summary'),
};

export default apiClient;
