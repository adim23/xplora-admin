import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WebPage from './web-page';
import WebPageDetail from './web-page-detail';
import WebPageUpdate from './web-page-update';
import WebPageDeleteDialog from './web-page-delete-dialog';

const WebPageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WebPage />} />
    <Route path="new" element={<WebPageUpdate />} />
    <Route path=":id">
      <Route index element={<WebPageDetail />} />
      <Route path="edit" element={<WebPageUpdate />} />
      <Route path="delete" element={<WebPageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WebPageRoutes;
