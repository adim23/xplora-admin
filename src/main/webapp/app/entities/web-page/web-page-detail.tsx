import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './web-page.reducer';

export const WebPageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const webPageEntity = useAppSelector(state => state.webPage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="webPageDetailsHeading">
          <Translate contentKey="xploraAdminApp.webPage.detail.title">WebPage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{webPageEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.webPage.code">Code</Translate>
            </span>
          </dt>
          <dd>{webPageEntity.code}</dd>
          <dt>
            <span id="uriPath">
              <Translate contentKey="xploraAdminApp.webPage.uriPath">Uri Path</Translate>
            </span>
          </dt>
          <dd>{webPageEntity.uriPath}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.webPage.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{webPageEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="publishDate">
              <Translate contentKey="xploraAdminApp.webPage.publishDate">Publish Date</Translate>
            </span>
          </dt>
          <dd>
            {webPageEntity.publishDate ? <TextFormat value={webPageEntity.publishDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.webPage.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {webPageEntity.createdDate ? <TextFormat value={webPageEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.webPage.createdBy">Created By</Translate>
          </dt>
          <dd>{webPageEntity.createdBy ? webPageEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.webPage.tags">Tags</Translate>
          </dt>
          <dd>
            {webPageEntity.tags
              ? webPageEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {webPageEntity.tags && i === webPageEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/web-page" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/web-page/${webPageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WebPageDetail;
