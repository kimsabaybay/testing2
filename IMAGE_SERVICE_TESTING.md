# Image Service API Testing Information

This document provides essential information about the Railway-deployed Product Mockup API for QA testing teams.

## System Overview

- **Platform**: Railway deployment
- **Base URL**: `https://your-app.railway.app`
- **Purpose**: Generate product mockups by combining design files with product templates
- **Input Methods**: URL-based (GET) or file upload (POST)

## API Endpoints

### Available Endpoints
- `GET /health` - Health check
- `GET /api/generate-mockup` - URL-based mockup generation
- `POST /api/generate-mockup` - File upload mockup generation

## Business Rules & Constraints

### File Requirements
- **Supported formats**: PNG, JPG, AI (Adobe Illustrator), EPS, PDF
- **Maximum file size**: 5MB
- **Minimum dimensions**: 100x100 pixels (recommended)
- **Maximum dimensions**: 4000x4000 pixels

### Template Requirements
- **Required parameters**: `templateName` (case-insensitive), `design` (URL or file)
- **Available templates**: `7102-Almond`, `7102-White`, `7102-Black`, `7102-Yellow`, `7102-Pink`
- **Optional parameter**: `displayName` (e.g., "Front", "Back", "Left", "Right")

### URL Requirements (for GET requests)
- **Design URLs**: Must be publicly accessible
- **Domain restrictions**: Only specific domains allowed in production
- **Protocol**: Must use HTTPS (HTTP not allowed)
- **Response time**: URLs must respond within 10 seconds

### Performance Expectations
- **Health check**: < 100ms response time
- **URL-based mockup**: 2-5 seconds processing time
- **File upload mockup**: 3-8 seconds processing time
- **Timeout limit**: 30 seconds maximum

## Environment Information

### Development vs Production
- **Development**: Allows `data:` URLs for testing file uploads
- **Production**: Restricts URLs to specific domains only
- **Environment detection**: Automatically detected by the API

### Rate Limiting
- **No explicit rate limits** currently implemented
- **Concurrent requests**: Supported (test with multiple simultaneous requests)
- **Resource usage**: Each request processes one image at a time

## Security Considerations

### File Upload Security
- **File type validation**: Strict validation of file extensions and MIME types
- **File size limits**: Enforced at both Express and Lambda levels
- **Temporary storage**: Uploaded files are automatically deleted after processing
- **No persistent storage**: Files are not stored on the server

### URL Security
- **Domain whitelist**: Only approved domains allowed in production
- **HTTPS enforcement**: HTTP URLs are rejected
- **Timeout protection**: Long-running requests are terminated

## Environment Configuration

### Environment Variables
- **NODE_ENV**: Automatically set by Railway
- **PORT**: Automatically assigned by Railway
- **No additional configuration required**


## Monitoring & Observability

### Health Check Endpoint
- **URL**: `GET /health`
- **Purpose**: Verify service availability
- **Response**: JSON with status and timestamp
- **Use case**: Load balancer health checks, monitoring

### Logging Information
- **Request logging**: All requests are logged
- **Error logging**: Detailed error information captured
- **Performance metrics**: Response times tracked
- **File cleanup**: Automatic cleanup of temporary files logged

### Debugging Information
- **Request IDs**: Each request gets a unique identifier
- **Error codes**: Specific error codes for different failure types
- **Stack traces**: Available in development environment
- **Response headers**: Include processing time and request ID

## System Architecture

### Core Components
- **Lambda function**: Image processing and template matching (original AWS Lambda code)
- **Express server**: HTTP request handling and routing (Railway wrapper - required because Railway doesn't natively support Lambda functions)
- **Multer middleware**: File upload processing and validation
- **Sharp library**: Image manipulation and conversion
- **Product data service**: Template information and lookup


### Dependencies
- **Image processing**: Sharp library for image manipulation
- **File system**: Temporary file storage (auto-cleanup)
- **Network**: External URL fetching for GET requests
- **Memory**: Image processing requires sufficient RAM

## API Endpoints

### Available Endpoints
- `GET /health` - Health check
- `GET /api/generate-mockup` - URL-based mockup generation
- `POST /api/generate-mockup` - File upload mockup generation

## Request/Response Formats

### Health Check
- **Method**: `GET /health`
- **Response**: `{"status":"OK","timestamp":"2024-01-15T10:30:00.000Z"}`

### URL-based Mockup Generation
- **Method**: `GET /api/generate-mockup`
- **Required Parameters**: `templateName`, `design` (URL)
- **Optional Parameters**: `displayName`
- **Response**: `{"success":true,"imageUrl":"data:image/png;base64,...","message":"Mockup generated successfully"}`

### File Upload Mockup Generation
- **Method**: `POST /api/generate-mockup`
- **Content-Type**: `multipart/form-data`
- **Required Parameters**: `templateName`, `design` (file)
- **Optional Parameters**: `displayName`
- **Response**: `{"success":true,"imageUrl":"data:image/png;base64,...","message":"Mockup generated successfully"}`

## Example Requests

### URL-based Request
```bash
curl -X GET "https://your-app.railway.app/api/generate-mockup?templateName=7102-Almond&design=https://ionic.io/img/trusted-partners/tp-logo-clearlyinnovative.png"
```

### File Upload Request
```bash
curl -X POST "https://your-app.railway.app/api/generate-mockup" -F "templateName=7102-Almond" -F "design=@your-image.png"
```

*Both endpoints return the same JSON format with base64 image data*

## Error Scenarios and Responses

### Complete Error Scenarios Table

| **Error Scenario** | **HTTP Status** | **Response Body** | **Error Source** | **Trigger Condition** |
|-------------------|-----------------|-------------------|------------------|----------------------|
| **Missing Parameters** |
| No template name (GET) | `400 Bad Request` | `{"error":"Missing templateName parameter"}` | Lambda Function | GET request without `templateName` |
| No template name (POST) | `400 Bad Request` | `{"success":false,"error":"Template name is required"}` | Express Validation | POST without `templateName` |
| No design parameter (GET) | `400 Bad Request` | `{"error":"Missing design parameter"}` | Lambda Function | GET request without `design` |
| No design file (POST) | `400 Bad Request` | `{"success":false,"error":"No design file provided"}` | Express Validation | POST without file upload |
| **File Upload Errors** |
| Invalid file type | `500 Internal Server Error` | `{"success":false,"error":"Only image files and design files (AI, EPS, PDF) are allowed"}` | Multer Filter | Uploading .txt, .doc, etc. |
| File too large (>5MB) | `500 Internal Server Error` | `{"success":false,"error":"File too large"}` | Multer Limits | File size exceeds 5MB |
| Corrupted image file | `500 Internal Server Error` | `{"success":false,"error":"Input buffer contains unsupported image format"}` | Sharp/Image Processing | Invalid image data |
| **Template Errors** |
| Template not found | `404 Not Found` | `{"error":"Template not found: {templateName}"}` | Lambda Function | Invalid template name |
| Invalid template name | `400 Bad Request` | `{"error":"Invalid templateName: {error details}"}` | Lambda Function | Malformed template name |
| **URL Validation Errors** |
| Invalid design URL | `400 Bad Request` | `{"error":"Invalid design URL: {error details}"}` | Lambda Function | Malformed URL in GET request |
| Base image domain not allowed | `400 Bad Request` | `{"error":"Base image URL domain not allowed"}` | Lambda Function | Base image from unauthorized domain |
| Design image domain not allowed | `400 Bad Request` | `{"error":"Design image URL domain not allowed"}` | Lambda Function | Design image from unauthorized domain |
| **File Size Validation** |
| Design file too large (Lambda) | `400 Bad Request` | `{"error":"Design file too large. Maximum allowed size is 5MB. Your file is {size}MB"}` | Lambda Function | File >5MB after processing |
| **Processing Errors** |
| Image analysis failed | `500 Internal Server Error` | `{"error":"Image analysis failed"}` | Lambda Function | Sharp/image processing error |
| Template data loading failed | `500 Internal Server Error` | `{"error":"Image analysis failed"}` | Lambda Function | Product data service error |
| **Server Errors** |
| Internal server error | `500 Internal Server Error` | `{"success":false,"error":"Internal server error"}` | Express Global Handler | Unhandled server error |
| Unknown error | `500 Internal Server Error` | `{"success":false,"error":"Unknown error occurred"}` | Express Error Handler | Unhandled exception |
| **Health Check** |
| Server running | `200 OK` | `{"status":"OK","timestamp":"2024-01-15T10:30:00.000Z"}` | Express Route | GET /health |

### Response Format Patterns

#### **Success Responses**
- **GET (Image)**: `200 OK` with PNG binary data
- **POST (JSON)**: `200 OK` with `{"success":true,"imageUrl":"data:image/png;base64,...","message":"Mockup generated successfully"}`

#### **Error Response Formats**

##### **Lambda Function Errors (GET)**
```json
{
  "error": "Error message description"
}
```

##### **Express Validation Errors (POST)**
```json
{
  "success": false,
  "error": "Error message description"
}
```

##### **Multer File Upload Errors (POST)**
```json
{
  "success": false,
  "error": "File upload error message"
}
```

### Error Handling Layers

| **Layer** | **Responsibility** | **Status Codes** | **Examples** |
|-----------|-------------------|------------------|--------------|
| **Express Validation** | Missing form data, basic validation | `400` | No file, no template name |
| **Multer Middleware** | File upload validation | `500` | File type, file size limits |
| **Lambda Function** | Business logic validation | `400`, `404`, `500` | Template lookup, URL validation, image processing |
| **Global Error Handler** | Unhandled exceptions | `500` | Server crashes, unknown errors |

### Status Code Guidelines

- **200**: Successful mockup generation
- **400**: Client errors (bad request, missing parameters, invalid data)
- **404**: Resource not found (template doesn't exist)
- **500**: Server errors (file processing, internal failures)
