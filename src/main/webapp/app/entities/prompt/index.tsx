import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prompt from './prompt';
import PromptDetail from './prompt-detail';
import PromptUpdate from './prompt-update';
import PromptDeleteDialog from './prompt-delete-dialog';

const PromptRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prompt />} />
    <Route path="new" element={<PromptUpdate />} />
    <Route path=":id">
      <Route index element={<PromptDetail />} />
      <Route path="edit" element={<PromptUpdate />} />
      <Route path="delete" element={<PromptDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PromptRoutes;
